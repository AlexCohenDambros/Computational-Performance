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
        int totalRamBits = (int) (Math.log(totalCache) / Math.log(2));


        System.out.println(kLineBits + " " + totalCacheBits + " " + totalRamBits);

        String w = Integer.toBinaryString(x & (kLine - 1));
        String r = Integer.toBinaryString(x >> kLineBits & (totalCache / kLine - 1));
        String t = Integer.toBinaryString(x >> (kLineBits + (totalCacheBits - kLineBits)));

        System.out.println(w);
        System.out.println(r);
        System.out.println(t);


    }

}
