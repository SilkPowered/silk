package cx.rain.silk.early_loading.worker;

import cx.rain.silk.Silk;

public class Hello {
    public static void printHello() {
        Silk.get().getLoaderLogger().info("Hello!");
        System.out.println("Hello2~");
    }
}
