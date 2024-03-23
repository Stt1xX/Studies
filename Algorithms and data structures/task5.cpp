#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> arr;
    int minimum = 10000000;
    for (int i = 0; i < n; i++) {
        int input;
        cin >> input;
        arr.push_back(input);
        if (i != 0) {
            minimum = min(arr[i] - arr[i - 1], minimum);
        }
    }
    int maximum = (arr[size(arr) - 1] - arr[0] + k - 2) / (k - 1);
    int current_value = 0;
    while (maximum != minimum) {
        int counter = 1;
        current_value = (minimum + maximum + 1) / 2;
        //cout << maximum << " " << current_value << " " << minimum << endl;
        int flag = 0;
        int temp = 0;
        for (int i = 1; i < n; i++) {
            int difference = arr[i] - arr[i - 1] + temp;
            if (difference >= current_value) {
                counter++;
                temp = 0;
            }
            else {
                temp = difference;
            }
            if (counter == k) {
                flag = 1;
                break;
            }
        }
        if (flag == 1) {
            minimum = current_value;
        }   
        else {
            maximum = current_value - 1;
        }
    }
    cout << maximum;
    return 0;
}