/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.subscriber.idtvsObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.beiker.xletview.app.App;

/**
 *
 * @author Felipe
 */
public class IDTVSObjectBinderApplication extends IDTVSObject {

    private HashMap<Long, Boolean> bindControll = new HashMap<>();
    private String mainFilePath;
    private String name;
    public AppRunType appRunType;
    private static final int MAX_BYTES_NAME = 32;
    private static final int MAX_BYTES_FILE_PATH = 250;

    public IDTVSObjectBinderApplication(long id, short type, File file) {
        super(id, type);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteBuffer byteBuffer;

            //Ler o primeiro byte com o tamanho tipo da aplicacao
            byte[] typeBytes = new byte[1];
            fis.read(typeBytes, 0, 1);
            this.appRunType = AppRunType.values()[ ((int) typeBytes[0] & 0xff)];

            //Ler o segundo byte com o tamanho válido para o file path 
            byte[] sizeMainFilePathBytes = new byte[1];
            fis.read(sizeMainFilePathBytes, 0, 1);
            int sizeMainFilePath = (int) sizeMainFilePathBytes[0] & 0xff;

            //Ler os bytes relativos ao file path
            byte[] mainFilePathBytes = new byte[sizeMainFilePath];
            fis.read(mainFilePathBytes, 0, sizeMainFilePath);
            this.mainFilePath = new String(mainFilePathBytes);

            //Skip para dados inválidos do file path
            fis.skip(MAX_BYTES_FILE_PATH - sizeMainFilePath);

            //Ler o byte com o tamanho válido para o nome 
            byte[] sizeNameBytes = new byte[1];
            fis.read(sizeNameBytes, 0, 1);
            int sizeName = (int) sizeNameBytes[0] & 0xff;

            //Ler os bytes relativos ao nome
            byte[] nameBytes = new byte[sizeName];
            fis.read(nameBytes, 0, sizeName);
            this.name = new String(nameBytes);

            //Skip para dados inválidos do nome
            fis.skip(MAX_BYTES_NAME - sizeMainFilePath);

            //Ler o byte relativo a quantidade de IDS do binder
            byte[] idsQuantityBytes = new byte[4];
            fis.read(idsQuantityBytes, 0, 4);
            byteBuffer = ByteBuffer.wrap(Arrays.copyOfRange(idsQuantityBytes, 0, 4));
            int idsQuantity = byteBuffer.getInt();

            //Ler todos os IDS do binder
            for (int i = 0; i < idsQuantity; i++) {
                byte[] idValueByte = new byte[8];
                fis.read(idValueByte, 0, 8);
                Long idValue = new Long(byteBuffer.getLong());
                this.bindControll.put(idValue, Boolean.FALSE);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IDTVSObjectBinderDirectory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IDTVSObjectBinderDirectory.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean bind(long idIDTVSObject) {
        if (this.bindControll.containsKey(idIDTVSObject)) {
            this.bindControll.put(idIDTVSObject, Boolean.TRUE);
            return true;
        }
        return false;
    }

    public boolean isBinded() {
        Collection<Boolean> values = bindControll.values();
        for (Boolean currentValue : values) {
            if (!currentValue) {
                return false;
            }
        }
        return true;
    }

    public App getAplication() {

        String mainClass = this.mainFilePath.substring(this.mainFilePath.lastIndexOf("\\") + 1, this.mainFilePath.lastIndexOf("."));
        ////TODO: [felipe] TRATAR ESSA CONCATENACAO COM SUBSCRIBER/
        String path = "Subscriber" + File.separator;
        path += this.mainFilePath.substring(0, this.mainFilePath.lastIndexOf("\\"));
        //path += File.separator;
        System.out.println("br.edu.ifba:: - APP PATH - " + path);
        return new App(name, path, mainClass);
    }

    public String getMainFilePath() {
        return mainFilePath;
    }

    public void setMainFilePath(String mainFilePath) {
        this.mainFilePath = mainFilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AppRunType getAppRunType() {
        return appRunType;
    }

    public void setAppRunType(AppRunType appRunType) {
        this.appRunType = appRunType;
    }
}
