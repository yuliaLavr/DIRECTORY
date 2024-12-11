import java.io.*;
import java.nio.file.*;
public class Main {
    public static void main(String[] args) {
        Path directory = Path.of("D:\\practdr\\task");
        try{
            if(!Files.exists(directory)){
                Files.createDirectory(directory);
                System.out.println("Directory created: " + directory.toAbsolutePath());
            }

            Path file=directory.resolve("1.txt");
            if(!Files.exists(file)){
                Files.createFile(file);
                System.out.println("File created: " + file.toAbsolutePath());
            }
            try (BufferedWriter w = Files.newBufferedWriter(file)) {
                w.write("Ім’я: Олександр, Вік: 45, Стать: Чоловік, student\n");
                w.write("Ім’я: Анна, Вік: 18, Стать: Жінка, student\n");
                w.write("Ім’я: Павло, Вік: 20, Стать: Чоловік, student\n");
            }
            long lines;
            try (BufferedReader r = Files.newBufferedReader(file)) {
                lines = r.lines().count();
            }
            System.out.println("Кількість рядків у файлі: " + lines);

            System.out.println("Введіть адресу файлу: ");
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String newFile = bufferedReader.readLine();

            Files.copy(file, Paths.get(newFile), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Файл скопійовано до: " + newFile);

            StudentToKursant(Path.of(newFile), "student", "kursant");
            System.out.println("Слова замінені у файлі: " + newFile);
        }catch (IOException e){
            System.err.println("ERROR while creating"+ e.getMessage());
        }
    }
    private static void StudentToKursant(Path file, String targetWord, String replacementWord) {
        try {
            String content = new String(Files.readAllBytes(file));
            content = content.replaceAll(targetWord, replacementWord);
            Files.write(file, content.getBytes());
        } catch (IOException e) {
            System.err.println("Помилка при заміні слів у файлі: " + e.getMessage());
        }
    }
}
