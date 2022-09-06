public class CacheLine {
    private int kLine;
    private int totalCache;
    private int totalRam;

    public CacheLine(int kLine, int totalCache, int totalRam) {
        this.kLine = kLine;
        this.totalCache = totalCache;
        this.totalRam = totalRam;
    }

    public void CalcularCacheLine(int x) {

        int kLineBits = (int) (Math.log(kLine) / Math.log(2));
        int totalCacheBits = (int) (Math.log(totalCache) / Math.log(2));
        int totalRamBits = (int) (Math.log(totalRam) / Math.log(2));


        System.out.println("Numero de bits em palavras em cache line: " + kLineBits);
        System.out.println("Numero de bits tamanho da cache:  " + totalCacheBits);
        System.out.println("Numero de bits tamanho da RAM: " + totalRamBits);


        int w = (x & (kLine - 1));
        int r = (x >> kLineBits & (totalCache / kLine - 1));
        int t = (x >> (kLineBits + (totalCacheBits - kLineBits)));

        System.out.println("Valor de w: " + w);
        System.out.println("Valor de r: " + r);
        System.out.println("Valor de t: " + t);


    }

}
