package com.wang.annotations_compiler;

import com.google.auto.service.AutoService;
import com.wang.annotations.BindView;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ViewCompiler extends AbstractProcessor {

    Filer filer;
    Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();

        messager.printMessage(Diagnostic.Kind.NOTE, "初始化ViewCompiler");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return processingEnv.getSourceVersion();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        /**
         * public class A{         TypeElement
         *  private int a;          VariableElement
         *  private void abc(){}    ExecutableElement
         *  }
         */
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(BindView.class);
        Map<String, List<VariableElement>> map = new HashMap<>();
        for (Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            //获取类名
            String className = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> list = map.get(className);
            if (list == null) {
                list = new ArrayList<>();
                map.put(className, list);
            }
            list.add(variableElement);
        }

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String className = iterator.next();
            List<VariableElement> list = map.get(className);
            //获取报名
            String packageName = processingEnv.getElementUtils().getPackageOf(list.get(0).getEnclosingElement()).toString();
            Writer writer = null;
            try {
                JavaFileObject javaFileObject = filer.createSourceFile(className + "$ViewBinder");
                writer = javaFileObject.openWriter();
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("package " + packageName + ";\n")
                        .append("\n")
                        .append("import com.wang.butterknife.IViewBinder;\n")
                        .append("\n")
                        .append("public class " + className + "$ViewBinder implements IViewBinder<" + packageName + "." + className + ">{\n")
                        .append("\t")
                        .append("public void bind(" + packageName + "." + className + " target){\n");

                for (VariableElement variableElement : list) {
                    //变量名字
                    String name = variableElement.getSimpleName().toString();
                    //变量类型
                    TypeMirror mirror = variableElement.asType();
                    //变量值
                    BindView bindView = variableElement.getAnnotation(BindView.class);
                    int id = bindView.value();
                    stringBuffer.append("\t\t")
                            .append("target." + name + " = " + "(" + mirror + ")target.findViewById(" + id + ");\n");
                }
                stringBuffer.append("\t}\n}");
                writer.write(stringBuffer.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
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
