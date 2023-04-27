package top.yonyong.vertx.demo.verticles.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import top.yonyong.vertx.demo.verticles.http.router.H2Router;
import top.yonyong.vertx.demo.verticles.http.router.TestRouter;

public class HttpVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    //h2
    Router h2Router = H2Router.createRouter(vertx);
    router.mountSubRouter("/h2", h2Router);
    //test
    Router testRouter = TestRouter.createRouter(vertx);
    router.mountSubRouter("/test", testRouter);

    router.route().handler(BodyHandler.create());
    router.errorHandler(501, this::errorHandler);
    vertx.createHttpServer().requestHandler(router).listen(8080,this::successHandler);
  }

  private void successHandler(AsyncResult<HttpServer> result) {
    if (result.succeeded()) {
      System.out.println("listen start");
    } else {
      System.out.println(result.cause().toString());
    }
  }

  private void errorHandler(RoutingContext context) {
    System.out.println("errorHandler");
    context.response().end(context.getBodyAsString());
  }

  @Override
  public void stop() throws Exception {
    System.out.println("stop ...");
  }
}
