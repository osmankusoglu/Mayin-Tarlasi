import java.util.Scanner;

public class MineSweeper {
    /*
     * MineSweeper sınıfı, mayın tarlası oyununu temsil eder.
     * Oyun tahtası, mayın haritası ve oyun durumu gibi özelliklere sahiptir.
     */
    public int row, column;                 // Mayın tarlasının satır ve sütun sayısı
    public char[][] userBoard;              // Kullanıcının gördüğü oyun tahtası
    public char[][] mineMap;                // Mayınların bulunduğu harita
    public boolean gameOver;                // Oyunun bitip bitmediğini belirten bayrak
    public int remainingCells;              // Açılması beklenen hücre sayısı
    public boolean isFirstMove = true;      // Oyunun ilk hareketi olup olmadığını kontrol eden bayrak
    public int getRemainingFoundCells;      // Mayın sayısı


    /*
     * Yeni bir MineSweeper nesnesi oluşturur ve oyunu temsil eden özellikleri başlatır.
     * @param row    Mayın tarlasının satır sayısı
     * @param column Mayın tarlasının sütun sayısı
     */
    public MineSweeper(int row, int column) {
        this.row = row;
        this.column = column;
        this.userBoard = new char[row][column];
        this.mineMap = new char[row][column];
        this.gameOver = false;
        this.remainingCells = row * column;

        // Oyunu başlatan metodu çağırır.
        initializeGame();
    }

    /*
     * Oyunu başlatan metot.
     * Kullanıcı tahtasını ('userBoard') '-' karakteri ile doldurur ve mayın haritasını oluşturmak için
     * 'initializeMineMap' metotunu çağırır.
     */
    public void initializeGame() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                userBoard[i][j] = '-';
            }
        }

        // Mayın haritasını oluşturmak için ilgili metodu çağırır.
        initializeMineMap();
    }

    /*
     * Mayın haritasını oluşturan metot.
     * Belirli bir mayın sayısı kadar '*' karakterini rasgele konumlarına yerleştirir.
     * Mayın haritasını oluşturduktan sonra kalan mayın sayısını 'getRemainingFoundCells' değişkenine atar.
     */
    public void initializeMineMap() {
        // Belirlenen mayın sayısını hesaplar.
        int mineCount = row * column / 4;

        // Kalan mayın sayısını 'getRemainingFoundCells' değişkenine atar.
        getRemainingFoundCells = mineCount;

        // Mayın haritasını '-' karakteri ile doldurur.
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                mineMap[i][j] = '-';
            }
        }

        // Belirlenen mayın sayısı kadar '*' karakterini rasgele konumlarına yerleştirir.
        int placedMines = 0;
        while (placedMines < mineCount) {
            int randomRow = (int) (Math.random() * row);
            int randomColumn = (int) (Math.random() * column);

            if (mineMap[randomRow][randomColumn] != '*') {
                mineMap[randomRow][randomColumn] = '*';
                placedMines++;
            }
        }
    }

    /*
     * Mayın haritasını ekrana yazdıran metot.
     * Her bir hücrenin değerini ekrana bastırır, mayın (*) varsa gösterir, yoksa '-' karakterini gösterir.
     *
     * @param row Oyun tahtasının satır sayısı
     * @param column Oyun tahtasının sütun sayısı
     * @param mineMap Oyun tahtasındaki mayın haritası
     */
    public void printMineMap() {
        // Mayın haritasını döngülerle gezerek her bir hücrenin değerini ekrana yazdırır.
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(mineMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    /*
     * Kullanıcının gördüğü oyun tahtasını ekrana yazdıran metot.
     * İlk hareket yapılmışsa, mayın haritasını gösterir ve kullanıcıya hoş geldin mesajını yazar.
     * İlk hareket yapılmamışsa, iç kısımdaki oyun tahtasını ekrana yazdırır.
     */
    public void printUserBoard() {
        // İlk hareket yapılmışsa
        if (isFirstMove) {
            System.out.println("Mayınların Konumu");

            // Mayın haritasını gösterir
            printMineMap();
            System.out.println("===========================");
            System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");

            // isFirstMove'u false olarak ayarlar, çünkü ilk hareket yapıldı.
            isFirstMove = false;
        } else {

            // İlk hareket yapılmamışsa, iç kısımdaki oyun tahtasını ekrana yazdırır.
            printUserBoardInternal();
        }
    }

    /*
     * Kullanıcının gördüğü oyun tahtasının iç kısmını ekrana yazdıran metod.
     * Oyun tahtasındaki her hücrenin değerini (mayın, boş ya da etrafındaki mayın sayısı) ekrana yazdırır.
     * Ekrandaki her satırı bir alt satıra geçerek yazdırır.
     */
    public void printUserBoardInternal() {
        // Oyun tahtasının her satırını döngü ile gez
        for (int i = 0; i < row; i++) {
            // Her satırdaki hücreleri döngü ile gez
            for (int j = 0; j < column; j++) {
                // Oyun tahtasındaki hücrenin değerini ekrana yazdır, boşluk bırak
                System.out.print(userBoard[i][j] + " ");
            }
            // Bir satırın sonunda bir alt satıra geç
            System.out.println();
        }
    }

    /*
     * MineSweeper oyununu başlatan ve kullanıcının oyunu oynamasını sağlayan metod.
     * Oyun tahtasını hazırlar, kullanıcıya ilk durumu gösterir ve kullanıcının hamlelerini alır.
     * Oyun bitene kadar kullanıcıdan hamle almayı sürdürür.
     */
    public void play() {
        // Oyunu başlat ve oyun tahtasını hazırla.
        initializeGame();

        // Kullanıcıya başlangıç durumunu göster ve ilk oyun tahtasını yazdır.
        printUserBoard();
        printUserBoardInternal();

        // Oyun bitene kadar kullanıcının hamlelerini al ve kontrol et.
        while (!gameOver) {
            int selectedRow = getUserInput("Satır girin: ");
            int selectedColumn = getUserInput("Sütun girin: ");
            System.out.println("===========================");

            // Geçerli bir giriş yapılmadığı sürece tekrar giriş iste.
            while (selectedRow < 0 || selectedRow >= row || selectedColumn < 0 || selectedColumn >= column) {
                selectedRow = getUserInput("Satır girin: ");
                selectedColumn = getUserInput("Sütun girin: ");
            }

            // Seçilen hücreyi kontrol et.
            checkSelectedCell(selectedRow, selectedColumn);

        }
    }

    /*
     * Kullanıcıdan giriş alarak bir tam sayı döndüren metot.
     * @param message Kullanıcıya gösterilecek mesaj
     * @return Kullanıcının girdiği tam sayı
     */
    public int getUserInput(String message) {
        System.out.print(message);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /*
     * Seçilen hücreyi kontrol eden metot. Eğer seçilen hücrede mayın varsa oyunu kaybettirir,
     * aksi takdirde hücrenin etrafındaki mayın sayısını hesaplar ve kullanıcı tahtasına bu sayıyı yazar.
     * Oyunu kazanıp kazanmadığını kontrol eder ve duruma göre ekrana mesaj yazdırır.
     * @param selectedRow Seçilen hücrenin satır indeksi
     * @param selectedColumn Seçilen hücrenin sütun indeksi
     */
    public void checkSelectedCell(int selectedRow, int selectedColumn) {

        // Seçilen hücrede mayın varsa
        if (mineMap[selectedRow][selectedColumn] == '*') {
            System.out.println("Oyunu kaybettiniz!");
            gameOver = true;
        } else {

            // Seçilen hücrede mayın yoksa yazdırır.
            int mineCount = countAdjacentMines(selectedRow, selectedColumn);
            userBoard[selectedRow][selectedColumn] = (char) (mineCount + '0');
            remainingCells--;

            // Kazanma durumu kontrolünü yapar.
            if (remainingCells == getRemainingFoundCells) {
                System.out.println("Tebrikler, oyunu kazandınız!");
                gameOver = true;
            } else {

                // Kazanma durumu gerçekleşmediyse güncellenmiş kullanıcı tahtasını yazdır.
                printUserBoard();
            }
        }
    }

    /*
     * Belirli bir hücrenin etrafındaki mayınları sayar.
     * row Seçilen hücrenin satır indeksi
     * col Seçilen hücrenin sütun indeksi
     * @return Etraftaki mayın sayısı
     */
    public int countAdjacentMines(int row, int col) {
        int mineCount = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;

                if (newRow >= 0 && newRow < this.row && newCol >= 0 && newCol < this.column) {
                    if (mineMap[newRow][newCol] == '*') {
                        mineCount++;
                    }
                }
            }
        }
        return mineCount;
    }

    /*
     * Kullanıcıdan satır ve sütun sayısını alarak yeni bir MineSweeper oyunu oluşturan ve başlatan
     * bir metod.
     * @return Oluşturulan ve başlatılan MineSweeper oyunu
     */
    public static MineSweeper Instance() {
        Scanner scanner = new Scanner(System.in);
        int rowNumber, colNumber;
        // Kullanıcıdan geçerli satır ve sütun sayıları alınıncaya kadar döngü devam eder.
        do {
            System.out.print("Satır sayısını girin : ");
            rowNumber = scanner.nextInt();

            System.out.print("Sütun sayısını girin : ");
            colNumber = scanner.nextInt();
            // Girilen değerlerin geçerli olup olmadığını kontrol eder.
            if (rowNumber < 2 || colNumber < 2) {
                System.out.println("Geçersiz giriş! Satır ve sütun sayısı en az 2 olmalıdır. Lütfen tekrar deneyin.");
            }
        } while (rowNumber < 2 || colNumber < 2);

        return new MineSweeper(rowNumber, colNumber);
    }

}