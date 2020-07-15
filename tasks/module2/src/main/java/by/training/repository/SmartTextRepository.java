package by.training.repository;

import by.training.model.TextComposite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SmartTextRepository implements Repository<TextComposite> {

    private final TextRepositoryTypes textRepoType;
    SmartTextRepository parent;
    SmartTextRepository child;

    /**
     * @return current text num
     */
    public abstract long getCurrentTextNum();

    /**
     * set current textNum to num if its valid
     * @param num toSet textNum
     */
    public abstract void setCurrentTextNum(long num);

    /**
     * @return repo type
     */
    public TextRepositoryTypes getTextRepoType() {
        return textRepoType;
    }

    public SmartTextRepository(TextRepositoryTypes textRepoType) {
        this.textRepoType = textRepoType;
    }

    /**
     * Searches for the desired repository
     * and adds to it composite
     * @param textComposite toAdd composite
     */
    public void smartAdd(TextComposite textComposite) {
        int compare = Integer.compare(textComposite.getTextPartType().getLevel(), textRepoType.getLevel());

        switch (compare) {
            case 0: {
                add(textComposite);
                break;
            }
            case 1: {
                if (child!=null) {
                    child.smartAdd(textComposite);
                }
                break;
            }
            case -1: {
                if (parent!=null) {
                    parent.smartAdd(textComposite);
                }
                break;
            }
        }
    }

    /**
     * Searches for the desired repository
     * and removes from it composite
     * @param textComposite toRemove composite
     * @return true if composite was removed,
     *  otherwise false
     */
    public boolean smartRemove(TextComposite textComposite) {
        int compare = Integer.compare(textComposite.getTextPartType().getLevel(), textRepoType.getLevel());

        switch (compare) {
            case 0: {
                return remove(textComposite);
            }
            case 1: {
                if (child!=null) {
                    return child.smartRemove(textComposite);
                }
            }
            case -1: {
                if (parent!=null) {
                    return parent.smartRemove(textComposite);
                }
            }
        }

        return false;
    }

    /**
     * Common realization of adding to repos
     * that contain map and have a "child-repo"
     * @param textComposite toAdd composite
     * @param map key-text number, content-list of text composites
     * @param repo 'child' repository
     */
    void addComposite(TextComposite textComposite, Map<Long, List<TextComposite>> map, SmartTextRepository repo) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (map.containsKey(parentId)) {
            map.get(parentId).add(textComposite);
        } else {
            map.put(parentId, new ArrayList<>(Collections.singletonList(textComposite)));
        }

        textComposite.getPartsList().forEach(repo::add);
    }

    /**
     * Common realization of removing from repos
     * that contain map and have a "child-repo"
     * @param textComposite toRemove composite
     * @param map key-text number, content-list of text composites
     * @param repo 'child' repository
     * @return true if composite was removed,
     * otherwise false
     */
    boolean removeComposite(TextComposite textComposite, Map<Long, List<TextComposite>> map, SmartTextRepository repo) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (!map.containsKey(parentId)) {
            return false;
        }

        if (map.get(parentId).contains(textComposite)) {
            textComposite.getPartsList().forEach(repo::remove);
            return map.get(parentId).remove(textComposite);
        }

        return false;
    }

    /**
     * Common realization of finding in repos
     * that contain map and have a "child-repo"
     * @param predicate search term
     * @param map key-text number, content-list of text composites
     * @param repo 'child' repository
     * @return found TextComposites
     */
    List<TextComposite> findComposite(Predicate<TextComposite> predicate, Map<Long, List<TextComposite>> map, SmartTextRepository repo) {
        long parentId = (parent!=null)?parent.getCurrentTextNum():-1;

        if (!map.containsKey(parentId)) {
            return Collections.emptyList();
        }

        List<TextComposite> res =
                map.get(parentId).stream().filter(predicate).collect(Collectors.toList());

        return (res.isEmpty())?repo.find(predicate):res;
    }
}
