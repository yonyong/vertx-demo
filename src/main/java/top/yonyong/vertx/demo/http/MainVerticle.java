package top.yonyong.vertx.demo.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Router router = Router.router(vertx);
    router.get("/test").handler(this::testHandler);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080)
      .onSuccess(this::onSuccessHandler);
  }

  private void testHandler(RoutingContext context) {
    System.out.println("request test");
    try {
      Thread.sleep(4000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    context.json(new JsonObject().put("status", 200).put("msg", "welcome"));
  }

  private void onSuccessHandler(HttpServer httpServer) {
    System.out.println("HTTP server started on port " + httpServer.actualPort());
  }
}
