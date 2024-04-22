#include <iostream>
#include <set>
#include <map>
#include <vector>
#include <utility>

using namespace std;

struct block {
	bool status;
	int size;
	int index;
	int number;
	struct block* previous;
	struct block* next;
};

int main() {

	auto cmp = [](pair<int, int> a, pair<int, int> b) {
		if (a.first != b.first) {
			return a.first > b.first;
		}
		return a.second < b.second;
		};

	map<pair<int, int>, struct block*, decltype(cmp)> free_blocks; // key - value : pair(size, index) - link
	map<int, struct block> all_blocks; // key - value : index - link
	map<int, struct block*> busy_blocks; /// key - value : number - link

	vector<int> result;

	int n, m;
	cin >> n >> m;

	struct block init_block = { true, n, 1, 0, NULL, NULL };
	all_blocks[1] = init_block;
	free_blocks[make_pair(n, 1)] = &all_blocks[1];

	for (int i = 1; i <= m; i++) {
		int current_request;
		cin >> current_request;
		if (current_request > 0) {
			if (free_blocks.size() == 0) {
				result.push_back(-1);
				continue;
			}
			struct block* max_free_block = free_blocks.begin()->second;
			if (max_free_block->size == current_request) {
				max_free_block->status = false;
				max_free_block->number = i;
				busy_blocks[i] = max_free_block;
				free_blocks.erase(free_blocks.begin());
				result.push_back(max_free_block->index);
				continue;
			}

			if (max_free_block->size > current_request) {
				int index = max_free_block->index + current_request;
				all_blocks[index] = { true, max_free_block->size - current_request, index, 0, max_free_block, max_free_block->next };
				max_free_block->status = false;
				max_free_block->size = current_request;
				max_free_block->number = i;
				max_free_block->next = &all_blocks[index];
				busy_blocks[i] = max_free_block;
				free_blocks.erase(free_blocks.begin());
				free_blocks[make_pair(all_blocks[index].size, all_blocks[index].index)] = &all_blocks[index];
				result.push_back(max_free_block->index);
				continue;
			}
			result.push_back(-1);
		}
		else {
			current_request *= -1;
			if (busy_blocks[current_request] == NULL) {
				continue;
			}
			struct block need_free = *busy_blocks[current_request];
			/*struct block* need_free = &(temp);*/

			need_free.status = true;
			busy_blocks.erase(need_free.number);

			if (need_free.next != NULL && need_free.next->status == true) {
				int next_size = need_free.next->size;
				int next_index = need_free.next->index;
				free_blocks.erase(make_pair(next_size, next_index));
				need_free = { true, need_free.size + need_free.next->size, need_free.index, 0, need_free.previous, need_free.next->next };
				all_blocks.erase(next_index);
			}
			if (need_free.previous != NULL && need_free.previous->status == true) {
				int previous_size = need_free.previous->size;
				int previous_index = need_free.previous->index;
				free_blocks.erase(make_pair(previous_size, previous_index));
				need_free = { true, need_free.size + need_free.previous->size, need_free.previous->index, 0, need_free.previous->previous, need_free.next };
			}

			all_blocks.erase(need_free.index);
			all_blocks[need_free.index] = need_free;
			if (need_free.previous != NULL)
				need_free.previous->next = &all_blocks[need_free.index];
			if (need_free.next != NULL)
				need_free.next->previous = &all_blocks[need_free.index];

			free_blocks[make_pair(need_free.size, need_free.index)] = &all_blocks[need_free.index];
		}
	}

	for (int x : result) {
		cout << x << endl;
	}

	return 0;
}