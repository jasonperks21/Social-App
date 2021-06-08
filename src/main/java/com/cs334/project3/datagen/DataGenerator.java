package com.cs334.project3.datagen;

import com.cs334.project3.model.*;
import com.cs334.project3.repo.*;
import lombok.NoArgsConstructor;
import org.hibernate.type.ZonedDateTimeType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component
public class DataGenerator {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private FriendRepository friendRepository;

    private List<String> messages_content = new ArrayList<>();
    private String[] names;
    private String[] group_names;
    private Random rand = new Random();

    private static int size = 10;

    public DataGenerator() {
        readFiles();
        System.out.println("Shakespeare size: " + messages_content.size());
    }


    private static String readLineByLineJava8(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

    private void readFiles(){
        String content = readLineByLineJava8("src\\main\\java\\com\\cs334\\project3\\datagen\\shakespeare.txt");
        Matcher m = Pattern.compile("\\n\\s\\s.*(\\n\\s\\s\\s.*)*").matcher(content);
        while(m.find()){
            messages_content.add(m.group().trim());
        }
        System.out.println("Read messages.txt");
        names = readLineByLineJava8("src\\main\\java\\com\\cs334\\project3\\datagen\\names.txt").split("\n");
        System.out.println("Read names.txt");
        Arrays.stream(names).forEach(String::trim);
        group_names = readLineByLineJava8("src\\main\\java\\com\\cs334\\project3\\datagen\\groups.txt").split("\n");
        System.out.println("Read groups.txt");
        Arrays.stream(names).forEach(String::trim);
    }

    private List<User> users = new ArrayList<>();
    private void createUsers(){
        List<String> l = Arrays.asList(names);
        Collections.shuffle(l);
        for(int i = 0; i < size; i++){
            String name = l.get(i);
            User u = new User(name, name + "@test.com", name, name);
            users.add(u);
        }
    }

    private List<Group> groups = new ArrayList<>();
    private List<GroupMember> members = new ArrayList<>();
    private void createGroups(){
        List<String> l = Arrays.asList(group_names);
        Collections.shuffle(l);
        for(int i = 0; i < size/3; i++){
            String name = l.get(i);
            Group g = new Group(name);
            groups.add(g);
        }

        outer:
        for(int i = 0; i < size * 2; i++){
            User u = randElem(users);
            Group g = randElem(groups);
            for(GroupMember m : u.getMemberships()){
                if(m.getGroup() == g) continue outer;
            }
            GroupMember gm = u.addGroupMembership(g, rand.nextBoolean());
            members.add(gm);
        }


    }

    private List<Post> posts = new ArrayList<>();
    private List<Category> categories;
    private void createPosts(){
        //create categories
//        categories.add(new Category("Shakespearean shit", "Some stuff about to be or not be."));
//        categories.add(new Category("More Shakespearean shit", "Some stuff about Romeo."));
//        categories.add(new Category("My fave play", "No one cares."));
//        categories.add(new Category("Sonnets suck", "They are slightly annoying."));
//        categories.add(new Category("Old English", "Grendel would like this."));


        //initial messages
        List<String> l = messages_content;
        Collections.shuffle(l);
        for(int i = 0; i < size*10; i++){
            String mes = l.get(i);
            Group group = randElem(groups);
            GroupMember member = randElem(members);
            Category cat = randElem(categories);
            Post p = new Post(group, member, cat, mes);
            ZonedDateTime time = ZonedDateTime.now();
            time = time.minusDays(10 + rand.nextInt(60)).minusHours(rand.nextInt(24));
            p.setTimestamp(time);
            posts.add(p);
        }


        //replies
        Collections.shuffle(l);
        for(int i = 0; i < size*8; i++){
            Post r = randElem(posts);
            String mes = l.get(i);
            ZonedDateTime time = r.getTimestamp();
            time = time.plusDays(rand.nextInt(40));
            if(time.isAfter(ZonedDateTime.now())){
                time = ZonedDateTime.now();
            }
            Post p = new Post(r, randElem(r.getMember().getGroup().getMembers()), mes);
            p.setTimestamp(time);
            posts.add(p);
        }

        GeometryFactory factory = new GeometryFactory();

        for (Post p : posts) {
            p.setLocation(factory.createPoint(
                    new Coordinate(20.0 + rand.nextDouble() * 10.0,
                            20.0 + rand.nextDouble() * 10.0)));
        }

    }

    private List<Friend> friendships = new ArrayList<>();
    private void createFriends(){
        for(User u: users){
            List<User> currentFriends = new ArrayList<>();
            int fsize = 1 + rand.nextInt(5);
            for(int i = 0; i < fsize; i++){
                List<GroupMember> memberships= u.getMemberships();
                if(memberships.size() != 0) {
                    Group g = randElem(memberships).getGroup();
                    User f = randElem(g.getMembers()).getUser();
                    if (!currentFriends.contains(f)) {
                        Friend friend = new Friend(u, f);
                        friendships.add(friend);
                        currentFriends.add(f);
                    }
                }
            }
        }
    }


    private <T> T randElem(List<T> list){
        if (list.size() > 0) {
            return list.get(rand.nextInt(list.size()));
        } else return null;
    }


    public void genData(){
        if (categories == null)
            categories = categoryRepository.findAll();
        createUsers();
        createGroups();
        createPosts();
        createFriends();
        userRepository.saveAll(users);
        groupRepository.saveAll(groups);
        groupMemberRepository.saveAll(members);
        postRepository.saveAll(posts);
        friendRepository.saveAll(friendships);
        //categoryRepository.saveAll(categories);
        //postRepository.saveAll(posts);
    }

}
