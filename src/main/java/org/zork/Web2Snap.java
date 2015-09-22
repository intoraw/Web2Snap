package org.zork;

import it.unimi.dsi.big.webgraph.ImmutableGraph;
import it.unimi.dsi.big.webgraph.LazyLongIterator;
import it.unimi.dsi.big.webgraph.NodeIterator;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zork on 9/22/15.
 */
public class Web2Snap {

  static ImmutableGraph ig;
  static NodeIterator ni;
  static FileWriter fw;
  static long num_e;
  static long num_v;
  static long count_e = 0L;

  public static boolean init(String fname_in, String fname_out){
    boolean ok = true;
    try {
      // init ig
      ig.loadOffline(fname_in);
      num_v = ig.numNodes();
      num_e = ig.numArcs();

      // init fw
      fw = new FileWriter(fname_out);

    } catch (IOException e) {
      e.printStackTrace();
      ok = false;
    }
    return ok;
  }


  private static boolean dump_edge(long src, long dst){
    boolean ok = true;
    try {
      fw.write(src + "\t" + dst + "\n");
      count_e += 1;
    } catch (IOException e){
      e.printStackTrace();
      ok = false;
    }
    return ok;
  }

  public static void visit_all(){
    while (ni.hasNext()){
      long src = ni.nextLong();
      LazyLongIterator adj_it = ni.successors();
      long dst;
      while ( (dst = adj_it.nextLong()) != -1 ) {
        dump_edge(src, dst);
      }
    }
  }


  public static void main(String args[]) {

    if (args.length != 2) {
      System.out.println("Invalid args.\n X.jar org.zork.Web2snap.java  <input_file> <output_file>");
    }
    init(args[0], args[1]);
    visit_all();
    System.out.println("Dump ok.");
    System.out.println("Num V : " + num_v + " Num E : " + num_e + " Count E : " + count_e);

  }

}
