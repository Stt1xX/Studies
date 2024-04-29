#include <iostream>
#include <unordered_map>
#include <vector>

using namespace std;

struct Node {
	int color = 1000;
	int parent;
};

int main() {

	unordered_map<int, Node> nodes;
	int n, colors_counter = 0;
	cin >> n;

	for (int i = 1; i <= n; i++) {
		int current_number;
		cin >> current_number;
		nodes[i] = { .parent = current_number };
	}

	for (int i = 1; i <= n; i++) {
		if (nodes[i].color == 1000) {
			colors_counter++;
			nodes[i].color = colors_counter;
			vector<int> childs = { i };
			int current_parent = nodes[i].parent;
			while (nodes[current_parent].color == 1000) {
				nodes[current_parent].color = colors_counter;
				childs.push_back(current_parent);
				current_parent = nodes[current_parent].parent;
			}
			if (nodes[current_parent].color < colors_counter) {
				int new_color = nodes[current_parent].color;
				for (int x : childs) {
					nodes[x].color = new_color;
				}
				colors_counter--;
			}
		}
	}
	cout << colors_counter;
	return 0;
}