#include <iostream>
#include <string>
#include <list>
#include <iterator>

using namespace std;

int main() {

	int n;
	cin >> n;
	list<int> main_list;
	list<int> result_list;
	list<int>::iterator iter;
	int current_pos;
	string current_sign, current_number;

	for (int i = 0; i < n; i++) {
		if (main_list.size() <= 1) {
			iter = main_list.begin();
			current_pos = 0;
		}
		else {
			int current_center = (main_list.size() + 1) / 2;
			advance(iter, current_center - current_pos);
			current_pos = current_center;
		}
		cin >> current_sign;
		if (current_sign == "*") {
			cin >> current_number;
			if (main_list.size() == 1) {
				main_list.push_back(stoi(current_number));
			}
			else {
				main_list.emplace(iter, stoi(current_number));
				current_pos += 1;
			}

		}
		if (current_sign == "+") {
			cin >> current_number;
			main_list.push_back(stoi(current_number));
		}
		if (current_sign == "-") {
			result_list.push_back(main_list.front());
			main_list.pop_front();
			current_pos--;
		}
	}

	for (int x : result_list) {
		cout << x << endl;
	}

	return 0;
}