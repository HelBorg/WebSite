package com.freelance.project.demo.repository.specifications;

import com.freelance.project.demo.controller.SkillController;
import com.freelance.project.demo.models.Filter;
import com.freelance.project.demo.models.SkillFilter;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.models.TaskSkill;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskSpecificationsBuilder {
    private List<SearchCriteria> params;
    private List<SearchCriteria> paramsSkills;

    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    public TaskSpecificationsBuilder(Filter filter, String pageName) {
        params = new ArrayList<>();
        paramsSkills = new ArrayList<>();
        params.add(new SearchCriteria("name", ":", filter.getFindName()));
        params.add(new SearchCriteria("createdTime", ">=", filter.getDateFrom()));
        params.add(new SearchCriteria("createdTime", "<=", filter.getDateTo()));
        params.add(new SearchCriteria("deadline", ">=", filter.getDueFrom()));
        params.add(new SearchCriteria("deadline", "<=", filter.getDueTo()));
        if ((!filter.getAuthor().equals(""))&(pageName.equals("tasks"))) {
            params.add(new SearchCriteria("author", ":", filter.getAuthor()));
        }
        switch (pageName) {
            case "candidate":
                params.add(new SearchCriteria("candidate_id", ":", filter.getId()));
                break;
            case "author":
                params.add(new SearchCriteria("author_id", ":", filter.getId()));
                break;
            case "in_work":
                System.out.println("in_work");
                params.add(new SearchCriteria("status", ":", "IN_WORK"));
                break;
            default:
                params.add(new SearchCriteria("status", ":", "PUBLISH"));
                break;
        }
        for (SkillFilter skill : filter.getFilterSkillsBy()) {
            paramsSkills.add(new SearchCriteria("skills", skill.getName(), ":", skill.getValueS()));
        }

    }

    //Building "or" and "and" separately, "or" for skills
    public Specification<Task> buildPartly(Boolean isOrPredicate) {
        int size = isOrPredicate ? paramsSkills.size() : params.size();
        if (size == 0) {
            return null;
        }

        List<Specification> specs = isOrPredicate ? paramsSkills.stream()
                .map(TaskSpecification::new).collect(Collectors.toList())
                : params.stream().map(TaskSpecification::new).collect(Collectors.toList());

        Specification result = specs.get(0);
        for (int i = 1; i < size; i++) {
            System.out.println("ppp " + i +"\n");
            result = isOrPredicate ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
        return result;
    }

    public Specification<Task> build() {
        logger.info(" build {}", params);
        logger.info(" build: {}", paramsSkills);
        return Specification.where(this.buildPartly(false).and(this.buildPartly(true)));
    }

}
