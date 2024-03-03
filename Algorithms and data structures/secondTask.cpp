#include <iostream>
#include <map>
#include <vector>

using namespace std;

int main() {
    string str;

    cin >> str;

    size_t len = str.length();

    map<int, char> strMap;
    map<int, int> animalMap;
    map<int, int> resultMap;

    int animal_key = 1;

    if(str.empty()){
        cout << "Possible";
        return 0;
    }

    for(int i = 0; i < str.size(); i++) {
        strMap[i] = str[i];
        if(islower(str[i])){
            animalMap[i] = animal_key++;
        }
    }


    int previous = 0;
    for(int current_symbol = 1; current_symbol < len; current_symbol++){
        if (strMap.find(previous)->second + 32 == strMap.find(current_symbol)->second){ // Ловушка до животного
            resultMap[previous] = animalMap.find(current_symbol) -> second;
            strMap.erase(previous);
            strMap.erase(current_symbol);
            if(strMap.lower_bound(previous) != strMap.begin()){
                previous = (--strMap.lower_bound(previous))->first;
            } else{
                current_symbol += 1;
                previous += current_symbol;
            }
        } else if (strMap.find(previous)->second == strMap.find(current_symbol)->second  + 32){ // Ловушка после животного
            resultMap[current_symbol] = animalMap.find(previous) -> second;
            strMap.erase(previous);
            strMap.erase(current_symbol);
            if(strMap.lower_bound(previous) != strMap.begin()){
                previous = (--strMap.lower_bound(previous))->first;
            } else{
                current_symbol += 1;
                previous += current_symbol;
            }
        } else{
            previous = current_symbol;
        }
    }
    if (resultMap.size() != str.length() / 2){
        cout << "Impossible";
        return 0;
    }
    cout << "Possible\n";
    for(auto & iter : resultMap){
        cout << iter.second << " ";
    }

    return 0;
}
