# Sub Arrays equaling K
def prefix_sums(arr: list[int]) -> list[int]:
    if len(arr) == 0:
        return []
    res: list[int] = [arr[0]]

    for i in range(1, len(arr)):
        res.append(arr[i] + res[i - 1])

    return res


def solve_sub_arrays(arr: list[int], k: int) -> list[tuple[int, int]]:
    sub_arrays: list[tuple[int, int]] = []
    prefix = prefix_sums(arr)

    sums_mapping: dict[int, list[int]] = dict()

    for i, local_sum in enumerate(prefix):
        if local_sum == k:
            sub_arrays.append((0, i))

        substruction = local_sum - k
        if substruction in sums_mapping:
            for elem_indx in sums_mapping[substruction]:
                sub_arrays.append((elem_indx + 1, i))

        rewriting = sums_mapping.get(local_sum, list())
        rewriting.append(i)
        sums_mapping[local_sum] = rewriting

    return sub_arrays


def solve_count(arr: list[int], k: int) -> int:
    mapping: dict[int, int] = dict()
    prefix_sum: int = 0
    count = 0
    mapping[0] = 1

    for i, elem in enumerate(arr):
        prefix_sum += elem
        substruction = prefix_sum - k

        count += mapping.get(substruction, 0)
        stored = mapping.get(prefix_sum, 0)
        mapping[prefix_sum] = stored + 1

    return count

# Java Version of Sub Arrays equaling K
#
# class Solution {
#     public int subarraySum(int[] nums, int k) {
#         int[] prefixSumArr = prefixSumArray(nums);
#         var subArrays = new ArrayList<int[]>(nums.length);
#         var mapping = new HashMap<Integer, List<Integer>>();
#
#         for (int i = 0; i < nums.length; i++) {
#             int prefix = prefixSumArr[i];
#
#             if (prefix == k) {
#                 subArrays.add(new int[]{0, i});
#             }
#
#             int substraction = prefix - k;
#
#             if (mapping.containsKey(substraction)) {
#                 for (Integer elemIndx: mapping.get(substraction)) {
#                     subArrays.add(new int[]{elemIndx + 1, i});
#                 }
#             }
#
#             var rewritting = mapping.getOrDefault(prefix, new ArrayList<Integer>());
#             rewritting.add(i);
#             mapping.put(prefix, rewritting);
#         }
#
#         return subArrays.size();
#     }
#
#     private int[] prefixSumArray(int[] origin) {
#         if (origin.length == 0) {
#             return new int[0];
#         }
#
#         int[] res = new int[origin.length];
#         res[0] = origin[0];
#
#         for (int i = 1; i < origin.length; i++) {
#             res[i] = origin[i] + res[i-1];
#         }
#
#         return res;
#     }
# }

# Java Version of Sub Arrays equaling K but only count
# class Solution {
#     public int subarraySum(int[] nums, int k) {
#         var mapping = new HashMap<Integer, Integer>();
#         mapping.put(0, 1);
#
#         int count = 0;
#         int prefix = 0;
#
#         for (int num: nums) {
#             prefix += num;
#             int sub = prefix - k;
#             count += mapping.getOrDefault(sub, 0);
#
#             int stored = mapping.getOrDefault(prefix, 0);
#             mapping.put(prefix, stored + 1);
#         }
#
#         return count;
#     }
# }
