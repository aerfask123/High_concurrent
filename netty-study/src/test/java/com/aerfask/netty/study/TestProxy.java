package com.aerfask.netty.study;


import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

public class TestProxy {

    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(new Target());

        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* foo(..))");
        Advisor advisor = new DefaultPointcutAdvisor(pointcut, (MethodInterceptor) invocation -> {
            try {
                System.out.println("before");
                return invocation.proceed();
            } finally {
                System.out.println("after");
            }
        });
        proxyFactory.addAdvisor(advisor);

        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("execution(* bar(..))");
        Advisor advisor2 = new DefaultPointcutAdvisor(pointcut2, (MethodInterceptor) invocation -> {
            try {
                System.out.println("before2...");
                return invocation.proceed();
            } finally {
                System.out.println("after2....");
            }
        });
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.setInterfaces(I1.class);
        proxyFactory.setProxyTargetClass(true); // true:使用CGLib   false：使用jdk

        Object proxy = proxyFactory.getProxy();
        System.out.print(proxy.getClass());
        I1 target = (I1) proxy;
        target.foo();
        target.bar();
    }
}

interface I1 {
    void foo();

    void bar();
}

class Target implements I1 {
    @Override
    public void foo() {
        System.out.println("foo");
    }

    @Override
    public void bar() {
        System.out.println("bar");
    }
}