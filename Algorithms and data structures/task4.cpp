#include <iostream>
#include <cmath>

using namespace std;

int main(){
    long days;
    double result, capacity, start, multiplier, destruction;

    cin >> start >> multiplier >> destruction >> capacity >> days;

    if (multiplier == 1){
        result = start - destruction * days;
        cout << max(0.0, result);
        return 0;
    }

    if (multiplier - destruction / start > 1 && (days >= capacity - start || start >= capacity)){
        cout << capacity;
        return 0;
    }
    if (multiplier - destruction / start < 1 && days >= start){
        cout << 0;
        return 0;
    }
    if (multiplier - destruction / start == 1){
        cout <<  min(start, capacity);
        return 0;
    }

    result = start * pow(multiplier, days) - destruction * (pow(multiplier, days)  - 1) / (multiplier - 1);
    cout << min(max(result, 0.0), capacity);
    return 0;
}