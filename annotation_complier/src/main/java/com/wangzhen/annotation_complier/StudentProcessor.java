package com.wangzhen.annotation_complier;

import com.google.auto.service.AutoService;
import com.wangzhen.annotation.Student;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
//@SupportedAnnotationTypes(com.wangzhen.annotation_complier.StudentProcessor)
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Student.class)
public class StudentProcessor extends AbstractProcessor {
    /**
     * init 方法会被注解处理工具调用
     *
     * @param processingEnvironment
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    /**
     * @param set              请求处理的注解类型
     * @param roundEnvironment
     * @return true:表示当前注解已经处理；false：可能需要后续的 processor 来处理
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Student.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                System.out.println(typeElement.getSimpleName());
                System.out.println("age:" + typeElement.getAnnotation(Student.class).age());
                System.out.println("height:" + typeElement.getAnnotation(Student.class).height() + " cm");
            }
        }
        return true;
    }

    /**
     * 设置这个处理器是处理什么类型注解的,这个可以用注解 @SupportedAnnotationTypes(com.wangzhen.annotation_complier.StudentProcessor) 来代替
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Student.class.getCanonicalName());
        return annotations;
    }

    /**
     * 制定使用的 java 版本，这个可以用注解 @SupportedSourceVersion(SourceVersion.RELEASE_8) 来代替
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
