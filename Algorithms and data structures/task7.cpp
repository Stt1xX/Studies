#include <iostream>
#include <map>
#include <vector>

using namespace std;

int main() {

	struct label {
		long long size;
		int count;
	};

	string str;
	cin >> str;
	map<char, struct label> lables;
	char current_label = 'a';
	long long input;
	for (int i = 0; i < 26; i++) {
		cin >> input;
		lables[current_label++] = (struct label) { input, 0};

	}
	vector<char> was_added; // contains keys
	for (int i = 0; i < str.length(); i++) {
		lables[str[i]].count++;
		if (lables[str[i]].count == 2) {
			was_added.push_back(str[i]);
			char cur_symbol = str[i];
			str.erase(i, 1);
			str.erase(str.find(cur_symbol), 1);
			i -= 2;
		}
		if (size(was_added) == 26) {
			break;
		}
	}
	//cout << str << endl;

	string str_to_add = "";
	for (int i = 0; i < size(was_added); i++) {
		int current_max = -1;
		int index = -1;
		for (int j = 0; j < size(was_added); j++) {
			if (lables[was_added[j]].size > current_max) {
				index = j;
				current_max = lables[was_added[j]].size;
			}
		}
		//cout << current_max << endl;
		str_to_add += was_added[index];
		was_added.erase(was_added.begin() + index);
		i--;
	}
	str = str_to_add + str;
	reverse(str_to_add.begin(), str_to_add.end());
	cout << str << str_to_add;
	return 0;
}