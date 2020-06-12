int main() {
    int a = 4;

    while (a >= 0) {
        a = a - 1;
    }

    if (a < 0) {
        a = a - 10;
    } else {
        a = a + 10;
    }

    for (int i = 0; i < 10; i = i + 1) {
        a = i * 2;
    }

    while (false) {
        a = 128;
    }

    do {
        a = a - 1;
    } while (a < 0);

    a = a * 2;
    a = a - 10;

    return 0;
}