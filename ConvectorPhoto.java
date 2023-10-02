import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.Base64;
// класс для конвертирования изображения
/**
 * Класс для конвертирования изображения
 * Смотреть класс {@link ConvectorPhoto}
 * @author Anuchin Dmitry
 * @version 1.0
 */
public class ConvectorPhoto {
    /**
     * Метод конвертирования изображения
     * @see ConvectorPhoto#images(FileInputStream)
     * @param inputS - файла изображения
     */
    public String images(FileInputStream inputS) throws IOException {
        ByteArrayOutputStream outputS = new ByteArrayOutputStream();
        byte[] b = new byte[4096];
        int bytesR = -1;

        while ((bytesR = inputS.read(b)) != -1) {
            outputS.write(b, 0, bytesR);
        }
        byte[] imageB = outputS.toByteArray();

        return Base64.getEncoder().encodeToString(imageB);
    }
}
