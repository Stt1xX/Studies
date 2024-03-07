#include <iostream>
#include <string>
#include <stack>
#include <vector>
#include <unordered_map>

using namespace std;

bool is_number(const string & s)
{
    if(s.empty() || ((!isdigit(s[0])) && (s[0] != '-') && (s[0] != '+'))) return false;
    char * p;
    strtol(s.c_str(), &p, 10);
    return (*p == 0);
}

stack<int> initialize(const int first_elem){
    stack<int> stack;
    stack.push(0);
    stack.push(first_elem);
    return stack;
}

string current_string;
stack<string> changed_on_this_cycle;
string separator = "separator";
unordered_map<string, stack<int>> main_map;

int main(){
    while(cin >> current_string){
        if(current_string == "{"){
            changed_on_this_cycle.push(separator);
            continue;
        }
        if(current_string == "}"){
            while(!changed_on_this_cycle.top().empty()){
                if (changed_on_this_cycle.top() == separator){
                    changed_on_this_cycle.pop();
                    break;
                }
                main_map[changed_on_this_cycle.top()].pop();
                changed_on_this_cycle.pop();
            }
            continue;
        }

        vector<string> current_vector = {current_string.substr(0, current_string.find('=')),
                                         current_string.substr(current_string.find('=') + 1, current_string.length() - 1)};
        if(main_map.find(current_vector[0]) != main_map.end()){
            if(is_number(current_vector[1])) {
                main_map[current_vector[0]].push(stoi(current_vector[1]));
            }
            else if(main_map.find(current_vector[1]) != main_map.end()){
                main_map[current_vector[0]].push(main_map[current_vector[1]].top());
                cout << main_map[current_vector[1]].top() << endl;
            } else{
                main_map[current_vector[0]].push(0);
                cout << 0 << endl;
            }
        } else{
            if(is_number(current_vector[1])){
                main_map[current_vector[0]] = initialize(stoi(current_vector[1]));
            } else if(main_map.find(current_vector[1]) != main_map.end()){
                main_map[current_vector[0]] = initialize(main_map[current_vector[1]].top());
                cout << main_map[current_vector[1]].top() << endl;
            } else{
                main_map[current_vector[0]] = initialize(0);
                cout << 0 << endl;
            }
        }
        changed_on_this_cycle.push(current_vector[0]);
    }
    return 0;
}