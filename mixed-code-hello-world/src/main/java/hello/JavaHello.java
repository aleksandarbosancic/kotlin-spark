package hello;

import hello.server.SparkServerKt;

import java.lang.management.ManagementFactory;

public class JavaHello {
  public static String JavaHelloString = "Hello from Java!";

  public static String getHelloStringFromKotlin() {
    return KotlinHelloKt.getKotlinHelloString();
  }

  public static void main(String[] args) {
    String vmName = ManagementFactory.getRuntimeMXBean().getName();
    int p = vmName.indexOf("@");
    String pid = vmName.substring(0, p);
    System.out.println("PID :" + pid);
    System.out.println(getHelloStringFromKotlin());
    System.out.println(KotlinHelloKt.getHelloStringFromJava());
    SparkServerKt.main(args);
  }
}
