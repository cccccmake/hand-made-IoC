package com.lagou.edu.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the factory class using reflection to create objects
 */
public class BeanFactory {
    private static Map<String, Object> map = new HashMap<>();

    static {
        // 1. parse the xml, using reflection to instantiate and store
        // load xml
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            // xPath expression: / => root node; //, . => current node; .. =>
            // parent node;
            List beanList = rootElement.selectNodes("//bean");
            for (int i = 0; i < beanList.size(); i++) {
                Element element = (Element) beanList.get(i);
                // proceed each element, get its id and class
                String id = element.attributeValue("id"); // accountDao
                String clazz = element.attributeValue("class"); // com.lagou...
                // using reflection to instantiate the objects and store them
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();
                map.put(id, o);
            }

            // after instantiation, check which classes need member value assignment
            // i.e., maintain the dependencies / dependency injection
            // tags with subtag named as "property" has such demand
            List propertyList = rootElement.selectNodes("//property");
            for (int i = 0; i < propertyList.size(); i++) {
                Element element = (Element) propertyList.get(i);
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");
                // look for its parent tag
                Element parent = element.getParent();
                // using parent element reflection
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);
                // iterate the methods within parentObject whose name equals
                // set+name
                Method[] methods = parentObject.getClass().getMethods();
                for (int j = 0; j < methods.length; j++) {
                    Method method = methods[j];
                    if(method.getName().equalsIgnoreCase("set"+name)){
                        method.invoke(parentObject, map.get(ref));
                    }
                }
                // update the parent object => its member variable has changed!
                map.put(parentId, parentObject);
            }
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    // 2. provide interface for other objects to get the instances (according
    // to bean id)
    public static Object getBean(String id){
        return map.get(id);
    }
}
