package com.cs334.project3.service;

import org.springframework.stereotype.Service;

@Service
public class BasicDisplayService {

//    @Autowired
//    private GroupRepository groupRepository;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    public GroupsThatUserIsMemberOfDTO getGroupsWhereUserIsMember(Long userId) {
//        //get from repo
//        GroupsThatUserIsMemberOfDTO dto = new GroupsThatUserIsMemberOfDTO();
//        List<Group> groupList = groupRepository.getALlGroupsThatUserIsMemberOf(userId);
//        try {
//            //format data
//            List<GroupDTO> groupDTOList = new ArrayList<>();
//            for (Group g : groupList) {
//                groupDTOList.add(new GroupDTO(g, userId));
//            }
//            dto.setData(groupDTOList);
//            dto.ok();
//        } catch (Exception e){
//            dto.error();
//        }
//
//        return dto;
//    }
//
//    public PostDTOProcessor getAllPostsToDisplayForUser(Long userId){
//        PostDTOProcessor pdto = new PostDTOProcessor();
//        try {
//            List<PostResultSetMapping> posts = postRepository.getAllPostsToDisplayForUser(userId);
//            //System.out.println(posts);
//            pdto.createRecursiveDTOStructure(posts);
//            pdto.ok();
//        } catch(Exception e){
//            e.printStackTrace();
//            pdto.error();
//        }
//        return pdto;
//    }
//
//    public PostDTOProcessor getAllPostsOfGroupToDisplayForUser(Long userId, Long groupId) {
//        PostDTOProcessor pdto = new PostDTOProcessor();
//        try {
//            List<PostResultSetMapping> posts = postRepository.getAllPostsOfGroupToDisplayForUser(userId, groupId);
//            //System.out.println(posts);
//            pdto.createRecursiveDTOStructure(posts);
//            pdto.ok();
//        } catch (Exception e) {
//            e.printStackTrace();
//            pdto.error();
//        }
//        return pdto;
//    }
}
