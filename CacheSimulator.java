import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CacheSimulator {
    public static void main(String[] args) {
//        String command = args[0];

        // number of sets in the cache
        int setNum = Integer.parseInt(args[1]);
        if (!(isPowerOf2(setNum))) {
            inputError();
        }

        // number of blocks in each set
        int blockNum = Integer.parseInt(args[2]);
        if (!(isPowerOf2(blockNum))) {
            inputError();
        }

        // number of bytes in each block
        int byteNum = Integer.parseInt(args[3]);
        if (!(isPowerOf2(byteNum)) || byteNum < 4) {
            inputError();
        }

        // write-allocate or no-write-allocate
        boolean writeAllocation = true;
        if (args[4].equals("write-allocate")) {
            writeAllocation = true;
        }
        else if (args[4].equals("no-write-allocate")) {
            writeAllocation = false;
        }
        else {
            inputError();
        }

        // write-through or write-back
        boolean writeThrough = true;
        if (args[5].equals("write-through")) {
            writeThrough = true;
        }
        else if (args[5].equals("write-back")) {
            writeThrough = false;
        }
        else {
            inputError();
        }

        // lru(least-recently-used, fifo, or random evictions
        int delete = 0;
        if (args[6].equals("lru")) {
            delete = 0; // lru (least-recently-used)
        }
        else if (args[6].equals("fifo")) {
            delete = 1; // fifo
        }
        else if (args[6].equals("random")) {
            delete = 2; // random
        }
        else {
            inputError();
        }

        // file name
        String traceFileName = args[7].replace("<", "");

        Cache c1 = new Cache(setNum, blockNum, byteNum, writeAllocation, writeThrough, delete);

        c1.readTraceFile(traceFileName);

        c1.printResult();
    }

    private static boolean isPowerOf2(int n) {
        return (n & (n - 1)) == 0;
    }

    private static void inputError() {
        System.out.println("input error");
        System.exit(-1);
    }
}

class Cache {
    int setNum, blockNum, byteNum;
    boolean writeAllocation, writeThrough;
    int delete;
    byte[][][] cacheMemory;

    int totalLoads = 0, totalStores = 0;
    int loadHits = 0, loadMisses = 0;
    int storeHits = 0, storeMisses = 0;
    int totalCycles = 0;

    public Cache(int setNum, int blockNum, int byteNum, boolean writeAllocation, boolean writeThrough, int delete) {
        this.setNum = setNum;
        this.blockNum = blockNum;
        this.byteNum = byteNum;
        this.writeAllocation = writeAllocation;
        this.writeThrough = writeThrough;
        this.delete = delete;

        cacheMemory = new byte[256][4][16];
    }

    void readTraceFile(String traceFileName) {
        String line;

        File traceFile = new File(traceFileName);
        try {
            Scanner traceFileReader = new Scanner(traceFile);
            while (traceFileReader.hasNextLine()) {
                line = traceFileReader.nextLine();
                String[] tokens = line.split(" ");
                if (tokens[0].equals("l")) {
                    boolean ls = true;
                }
                else if (tokens[1].equals("s")) {
                    boolean ls = false;
                }
                String address = tokens[1];
                // 세번째 필드는 이 과제에서는 무시
            }
            traceFileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void printResult() {
        System.out.println("Total loads: " + totalLoads);
        System.out.println("Total stores: " + totalStores);
        System.out.println("Load hits: " + loadHits);
        System.out.println("Load misses: " + loadMisses);
        System.out.println("Store hits: " + storeHits);
        System.out.println("Store misses: " + storeMisses);
        System.out.println("Total cycles: " + totalCycles);
    }
}