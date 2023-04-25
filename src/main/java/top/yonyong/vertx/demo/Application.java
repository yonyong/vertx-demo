package top.yonyong.vertx.demo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import top.yonyong.vertx.demo.verticles.lifecycle.LifeCycleVerticle;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Application {

  public static void main(String[] args) {
    Application.way2();
  }

  private static void way1() {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MainVerticle());
  }

  private static void way2(){
    final ConcurrentMap<String, String> ids = new ConcurrentHashMap<>();
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(LifeCycleVerticle::new, new DeploymentOptions().setInstances(4), res -> {
      if (res.succeeded()) {
        ids.put(res.result(), res.result());
      }
    });
    vertx.deployVerticle(LifeCycleVerticle::new, new DeploymentOptions().setInstances(3), res -> {
      if (res.succeeded()) {
        ids.put(res.result(), res.result());
      }
    });
    Runtime.getRuntime().addShutdownHook(new Thread(()-> ids.keySet().forEach(item -> vertx.undeploy(item, res -> System.out.println("Successfully undeploy the item: " + item)))));
    System.out.println("finish");
  }
}
