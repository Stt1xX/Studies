#include <iostream>
#include <set>
#include <list>
using namespace std;
int main() {
	multiset<int> main_set;
	list<int> prev_numbers;
	int n, k;
	cin >> n >> k;
	for (int i = 0; i < k; i++) {
		int current_number;
		cin >> current_number;
		main_set.insert(current_number);
		prev_numbers.push_back(current_number);
	}
	cout << *main_set.begin() << " ";
	for (int i = 0; i < n - k; i++) {
		int current_number;
		cin >> current_number;
		main_set.extract(prev_numbers.front());
		prev_numbers.pop_front();
		main_set.insert(current_number);
		prev_numbers.push_back(current_number);
		cout << *main_set.begin() << " ";
	}
	return 0;
}