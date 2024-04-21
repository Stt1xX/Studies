#include <iostream>
#include <unordered_map>
#include <set>
#include <list>
#include <vector>

using namespace std;

list<int> main_array[100001]{};
int all_toys[500001]{};

int main() {
	
	int n, k, p;
	cin >> n >> k >> p;
	int main_counter = 0;

	for (int i = 0; i < p; i++) {
		int current_toy;
		cin >> current_toy;
		main_array[current_toy].push_back(i);
		all_toys[i] = current_toy;
	}

	auto cmp = [](int a, int b) {
		if (main_array[b].size() == 0) {
			return false;
		}
		if (main_array[a].size() == 0) {
			return true;
		}
		return main_array[a].front() > main_array[b].front();
	};

	set<int, decltype(cmp)> current_toys(cmp);
	for (int i = 0; i < p; i++) {
		if (current_toys.contains(all_toys[i])) {
			current_toys.erase(all_toys[i]);
			main_array[all_toys[i]].pop_front();
			current_toys.insert(all_toys[i]);
		}
		else {
			if (current_toys.size() == k) {
				current_toys.erase(current_toys.begin());
			}
			main_array[all_toys[i]].pop_front();
			current_toys.insert(all_toys[i]);
			main_counter++;
		}
	}

	cout << main_counter << endl;
	return 0;
}