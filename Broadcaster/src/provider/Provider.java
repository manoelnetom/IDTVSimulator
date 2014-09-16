/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package provider;

import br.edu.ifba.broadcaster.carousel.Carousel;
import br.edu.ifba.broadcaster.carousel.CarouselNode;
import br.edu.ifba.broadcaster.carousel.IDTVSObjectFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.JFileChooser;
import br.edu.ifba.broadcaster.udpprotocol.Protocol;
import br.edu.ifba.broadcaster.udpprotocol.ProtocolPacket;
import br.edu.ifba.broadcaster.updconnection.ConnectionSender;

/**
 *
 * @author Thiago
 */
public class Provider {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws FileNotFoundException, IOException {
//
//        ConnectionSender connSender = new ConnectionSender("127.0.0.1", 2222);
//                                        
//        final long id = 1;
//        final short type = 1;
//        final short version = 1;
//                
//        // tempo em milisegundos (50)
//        Carousel carousel = new Carousel(50, connSender);
//        carousel.startCarousel();
//        JFileChooser j = new JFileChooser();
//        j.showOpenDialog(null);
//        File file = j.getSelectedFile();
//       // carousel.addNode(new CarouselNode<>(new IDTVSObjectFile(id, type, version,file, Protocol.DATA_SIZE, null)));
//                
////        j = new JFileChooser();
////        j.showOpenDialog(null);
////        File file2 = j.getSelectedFile();
////        
////        carousel.addNode(new CarouselNode<>(new IDTVSObjectFile((id+1), type, version,file2, Protocol.DATA_SIZE)));
//    }
}
