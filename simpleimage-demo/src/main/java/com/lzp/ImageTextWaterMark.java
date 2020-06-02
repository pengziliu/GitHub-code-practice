package com.lzp;

import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.render.*;
import org.apache.commons.io.IOUtils;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ImageTextWaterMark {
    final static File        path              = new File("/Users/mac/Downloads/压缩前.jpeg");
    final static File        rpath             = new File("/Users/mac/Downloads/加水印.jpeg");
    final static Font  FONT              = new Font("黑体", Font.PLAIN, 20);

    public static void main(String[] args) throws Exception {

        FixDrawTextItem item = new FixDrawTextItem("保住头发别秃！", Color.BLACK, Color.BLACK,
                FONT, 10, FixDrawTextItem.Position.TOP_LEFT, 1f);
        doDrawImageText(item);

    }


    public static void doDrawImageText(DrawTextItem... items) throws Exception {
        InputStream in = null;
        ImageRender fr = null;
        try {
            in = new FileInputStream(path);
            ImageRender rr = new ReadRender(in);

            DrawTextParameter dp = new DrawTextParameter();

            if (items != null) {
                for (DrawTextItem itm : items) {
                    dp.addTextInfo(itm);
                }
            }
            DrawTextRender dtr = new DrawTextRender(rr, dp);
            fr = new WriteRender(dtr,rpath);
            fr.render();
        } finally {
            IOUtils.closeQuietly(in);
            if (fr != null) {
                fr.dispose();
            }
        }
    }
}
