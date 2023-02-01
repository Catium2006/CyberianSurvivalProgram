package top.catium.csp.file;

import java.io.*;

public class TextFile {


    /**
     * 读文件
     * @param fileName
     * @return 读到的文件内容
     */
    public static String read(String fileName){
        String res = "";
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行,直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 追加系统换行符
                res += tempString + System.lineSeparator();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return res;
    }

    /**
     * 追加文件
     * @param fileName 文件名
     * @param content 内容
     * @return 是否成功
     */
    public static boolean append(String fileName, String content){
        try {
            FileWriter fw = new FileWriter(fileName,true);
            fw.write(content);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 覆盖文件
     * @param fileName 文件名
     * @param content 内容
     * @return 是否成功
     */
    public static boolean write(String fileName, String content){
        try {
            FileWriter fw = new FileWriter(fileName,false);
            fw.write(content);
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
