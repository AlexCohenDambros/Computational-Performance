public class Main {
    public static void main(String[] args) {
        CacheLine cache = new CacheLine(32, 4*1024, 16*1024*1024);
        cache.CalcularCacheLine(10560325);
    }
}
