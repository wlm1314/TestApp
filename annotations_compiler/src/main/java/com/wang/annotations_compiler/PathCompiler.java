package com.wang.annotations_compiler;

import com.google.auto.service.AutoService;
import com.wang.annotations.BindPath;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class PathCompiler extends AbstractProcessor {
    //生成文件对象
    Filer filer;

    /**
     * 初始化的第一个方法
     *
     * @param processingEnv
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    /**
     * 返回注解处理器要处理的注解
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(BindPath.class.getCanonicalName());
        return types;
    }

    /**
     * 声明注解处理器支持的java版本
     *
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    /**
     * 注解处理器的核心方法 写文件 写代码
     *
     * @param annotations
     * @param roundEnv
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //拿到该模块中所有用到BindPath注解的节点
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindPath.class);
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            BindPath annotation = typeElement.getAnnotation(BindPath.class);
            //获取key
            String key = annotation.value();
            //带报名的类名
            String activityName = typeElement.getQualifiedName().toString();
            map.put(key, activityName);
        }

        if (map.size() > 0) {
            //写文件
            Writer writer = null;
            String javaName = "Activity" + System.currentTimeMillis();
            try {
                JavaFileObject javaFileObject = filer.createSourceFile("com.wang.util." + javaName);
                writer = javaFileObject.openWriter();
                writer.write("package com.wang.util;\n" +
                        "\n" +
                        "import com.wang.router.IRouter;\n" +
                        "import com.wang.router.WsyRouter;\n" +
                        "\n" +
                        "public class " + javaName + " implements IRouter {\n" +
                        "\n" +
                        "   @Override\n" +
                        "   public void putActivity(){\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String value = map.get(key);
                    writer.write("      WsyRouter.getInstance().putActivity(\"" + key + "\"," + value + ".class);\n");
                }
                writer.write("  }\n}");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer != null){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
