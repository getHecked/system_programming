#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void)
{
    FILE *fi, *fo;
    char fr[256] = { };

    char buf[1024];

    printf("Enter input file name: ");
    scanf("%255s", fr);

    fi = fopen(fr, "r");
    if(!fi) {
        printf("Failed to open input file\n");
        return -1;
    }

    while(!feof(fi)) {
        memset(buf, 0, 1024);
        size_t s = fread(buf, 1, 1024, fi);
        fputc(ch, "temp.txt");
    }

    fclose(fi);
    return 0;
}
