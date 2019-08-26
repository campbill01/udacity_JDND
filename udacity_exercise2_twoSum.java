
class Main {
  public static void main(String[] args) {
    int[] input1 = {1,2,3,4};
    int[] input2 = {1,4,5,1,6};
    System.out.println(twoSum(input1, 5));
    System.out.println(twoSum(input2, 12));
  }

  public static boolean twoSum(int[] nums, int target) {
    // for number in nums, add to each consecutive number and see if it matches target
    for(int i=0; i < nums.length -1; i++){
      if(nums[i] + nums[i+1] == target){
          return true;
        }
      }
    return false;
    }
  }