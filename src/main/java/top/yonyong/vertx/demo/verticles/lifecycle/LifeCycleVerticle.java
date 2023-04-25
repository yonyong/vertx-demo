package top.yonyong.vertx.demo.verticles.lifecycle;

import io.vertx.core.AbstractVerticle;

public class LifeCycleVerticle extends AbstractVerticle {
  @Override
  public void start() {
    System.out.println(Thread.currentThread().getName() +
      ": Start : " +
      Thread.currentThread().getId()
      + ", Did: " + this.deploymentID());
  }

  @Override
  public void stop() {
    System.out.println(Thread.currentThread().getName() +
      ": Stop : " +
      Thread.currentThread().getId()
      + ", Did: " + this.deploymentID());
  }
}
