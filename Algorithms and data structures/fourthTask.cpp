#include <iostream>
#include <cmath>

using namespace std;

int main(){
    long days;
    double result, capacity, start, multiplier, destroy;

    cin >> start >> multiplier >> destroy >> capacity >> days;

    if (multiplier == 1){
        result = start - destroy * days;
        cout << max(0.0, result);
        return 0;
    }

    if (multiplier - destroy / start > 1 && (days >= capacity - start || start >= capacity)){
        cout << capacity;
        return 0;
    }
    if (multiplier - destroy / start < 1 && (days > capacity || days >= start)){
        cout << 0;
        return 0;
    }
    if (multiplier - destroy / start == 1){
        cout << start;
        return 0;
    }

    result = start * pow(multiplier, days) - destroy * (pow(multiplier, days)  - 1) / (multiplier - 1);
    cout << min(max(result, 0.0), capacity);
    return 0;
}