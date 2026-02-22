package ma.enset.framework;

import ma.enset.framework.annotations.Inject;

import org.w3c.dom.*;
import javax.xml.parsers.*;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MiniApplicationContext {

    private Map<String, Object> beans = new HashMap<>();

    // =========================
    // 🔹 CONSTRUCTEUR XML
    // =========================
    public MiniApplicationContext(String xmlFilePath) throws Exception {

        // 1️⃣ Lire le fichier XML
        DocumentBuilder builder =
                DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(new File(xmlFilePath));

        NodeList beanNodes = document.getElementsByTagName("bean");

        // 2️⃣ Instancier tous les beans
        for (int i = 0; i < beanNodes.getLength(); i++) {
            Element bean = (Element) beanNodes.item(i);

            String id = bean.getAttribute("id");
            String className = bean.getAttribute("class");

            Class<?> clazz = Class.forName(className);
            Object instance = clazz.getDeclaredConstructor().newInstance();

            beans.put(id, instance);
        }

        // 3️⃣ Injection des dépendances (Setter)
        for (int i = 0; i < beanNodes.getLength(); i++) {
            Element bean = (Element) beanNodes.item(i);

            Object targetObject = beans.get(bean.getAttribute("id"));
            NodeList properties = bean.getElementsByTagName("property");

            for (int j = 0; j < properties.getLength(); j++) {
                Element property = (Element) properties.item(j);

                String propertyName = property.getAttribute("name");
                String ref = property.getAttribute("ref");

                Object dependency = beans.get(ref);

                String setterName = "set" + capitalize(propertyName);
                Method setter = targetObject.getClass()
                        .getMethod(setterName, dependency.getClass().getInterfaces()[0]);

                setter.invoke(targetObject, dependency);
            }
        }

        // 4️⃣ Injection par annotations (@Inject)
        injectByAnnotations();
    }

    // =========================
    // 🔹 INJECTION PAR ANNOTATIONS (FIELD)
    // =========================
    private void injectByAnnotations() throws Exception {
        for (Object bean : beans.values()) {
            Field[] fields = bean.getClass().getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(Inject.class)) {
                    for (Object dependency : beans.values()) {
                        if (field.getType().isAssignableFrom(dependency.getClass())) {
                            field.setAccessible(true);
                            field.set(bean, dependency);
                        }
                    }
                }
            }
        }
    }

    // =========================
    // 🔹 RÉCUPÉRER UN BEAN
    // =========================
    public Object getBean(String id) {
        return beans.get(id);
    }

    // =========================
    // 🔹 UTILITAIRE
    // =========================
    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}