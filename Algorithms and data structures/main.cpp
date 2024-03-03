#include <iostream>

using namespace std;

int main() {
    int n;
    int previous, preprevious;
    int arr[200005];
    int currentLength = 2, index, maxLength = -1;

    cin >> n;
    for (int i = 0; i < n; i++){
        cin >> arr[i];
    }

    if (n > 2){
        previous = arr[n - 2];
        preprevious = arr[n - 1];
        index = n - 1;
    } else{
        cout << 1 << " " << n;
        return 0;
    }

    for (int i = n - 3; i >= 0; i--){
        if (preprevious == previous and previous == arr[i]){
            currentLength = 2;
        } else {
            currentLength++;
        }
        preprevious = previous;
        previous = arr[i];
        if (maxLength <= currentLength){
            index = i + currentLength - 1;
            maxLength = currentLength;
        }
    }

    cout << index - maxLength + 2 << " " << index + 1;

    return 0;
}
