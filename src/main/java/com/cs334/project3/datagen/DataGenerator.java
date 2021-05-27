package com.cs334.project3.datagen;

import com.cs334.project3.model.*;
import com.cs334.project3.repo.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private List<String> messages_content = new ArrayList<>();
    private String[] names;
    private String[] group_names;
    private Random rand = new Random();

    private static int size = 50;

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
        for(int i = 0; i < size/10; i++){
            String name = l.get(i);
            Group g = new Group(name);
            groups.add(g);
        }

        for(int i = 0; i < (int)((float)size*1.5f); i++){
            User u = randElem(users);
            Group g = randElem(groups);
            members.add(new GroupMember(g, u, rand.nextBoolean()));
        }


    }

    private List<Post> posts = new ArrayList<>();
    private List<Category> categories = new ArrayList<>();
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
            posts.add(p);
        }


        Collections.shuffle(l);
        for(int i = 0; i < size*8; i++){
            Post r = randElem(posts);
            String mes = l.get(i);
            Post p = new Post(r, randElem(r.getMember().getGroup().getMembers()), mes);
            posts.add(p);
        }

    }


    private <T> T randElem(List<T> list){
        return list.get(rand.nextInt(list.size()));
    }


    public void genData(){

        categories = categoryRepository.findAll();
        createUsers();
        System.out.println("Created 100 users.");
        createGroups();
        System.out.println("Created 10 users.");
        createPosts();
        System.out.println("Created 1000 posts.");
        //userRepository.saveAll(users);
        postRepository.saveAll(posts);
        //categoryRepository.saveAll(categories);
        //groupMemberRepository.saveAll(members);
        //postRepository.saveAll(posts);
    }

}
