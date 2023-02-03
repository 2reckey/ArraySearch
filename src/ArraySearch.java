class ArraySearch {
    public static int goldenSectionSearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        double phi = (Math.sqrt(5) - 1) / 2;

        while (left <= right) {
            int mid = left;
            if (key - array[left] < array[right] - key) mid += (int) ((right - left) * (1 - phi));
            else mid += (int) ((right - left) * phi);

            if (array[mid] < key) {
                left = mid + 1;
            } else if (array[mid] > key) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int NewtonSearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        if (array[left++] == key) return left;
        if (array[right--] == key) return right;
        int x = right >>> 1;
        while (array[x] != key) {
            if (array[x] < key && x >= left) left = x + 1;
            else right = x - 1;
            x -= 2 * (array[x] - key) / (array[x + 1] - array[x - 1]);
            if (x < left || x > right) x = x < left ? left : right;
        }
        return x;
    }

    public static int binarySearch(int[] array, int key) {
        int left = 0;
        int right = array.length - 1;
        while (left <= right) {
            int mid = (left + right) >>> 1;
            if (array[mid] < key) {
                left = mid + 1;
            } else if (array[mid] > key) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static int binarySearch(int[] array, int key, int left, int right) {
        if (left > right) return -1;
        int mid = (left + right) >>> 1;
        if (array[mid] < key) {
            return binarySearch(array, key, mid + 1, right);
        } else if (array[mid] > key) {
            return binarySearch(array, key, left, mid - 1);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        long startTime;
        long endTime;
        long duration;

        int n = 100000000;
        int[] array = new int[n];
        array[0] = 1;
        for (int i = 1; i < n; i++) {
            array[i] = array[i - 1] + 1 + (int) (Math.random() * 3);
        }
        int key = array[(int) (Math.random() * n + 1)];
        long[] time = new long[3];
        int m = 1000000;
        for (int i = 0; i < m; ++i) {
            key = array[(int) (Math.random() * n)];
            if (i % (3 * m / 100) == 0) System.out.println(i / 3 * 100 / m + "%");

            startTime = System.nanoTime();
            binarySearch(array, key, 0, array.length - 1);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            time[0] += duration;
        }
        for (int i = m; i < 2 * m; ++i) {
            key = array[(int) (Math.random() * n)];
            if (i % (3 * m / 100) == 0) System.out.println(i / 3 * 100 / m + "%");

            startTime = System.nanoTime();
            goldenSectionSearch(array, key);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            time[1] += duration;
        }
        for (int i = 2 * m; i < 3 * m; ++i) {
            key = array[(int) (Math.random() * n)];
            if (i % (3 * m / 100) == 0) System.out.println(i / 3 * 100 / m + "%");

            startTime = System.nanoTime();
            NewtonSearch(array, key);
            endTime = System.nanoTime();
            duration = (endTime - startTime);
            time[2] += duration;
        }

        System.out.println("Позиция ключа: " + binarySearch(array, key));
        System.out.println("Среднее время бинарного поиска: " + time[0] / m + " ns");

        System.out.println("Позиция ключа: " + goldenSectionSearch(array, key));
        System.out.println("Среднее время поиска метотодом золотого сечения: " + time[1] / m + " ns");

        System.out.println("Позиция ключа: " + NewtonSearch(array, key));
        System.out.println("Среднее время поиска методом Ньютона: " + time[2] / m + " ns");

    }
}