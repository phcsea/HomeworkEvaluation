package com.buguagaoshu.homework.evaluation.service.impl;

import com.buguagaoshu.homework.evaluation.entity.HomeworkEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buguagaoshu.homework.common.utils.PageUtils;
import com.buguagaoshu.homework.common.utils.Query;

import com.buguagaoshu.homework.evaluation.dao.SubmitHomeworkStatusDao;
import com.buguagaoshu.homework.evaluation.entity.SubmitHomeworkStatusEntity;
import com.buguagaoshu.homework.evaluation.service.SubmitHomeworkStatusService;


@Service("submitHomeworkStatusService")
public class SubmitHomeworkStatusServiceImpl extends ServiceImpl<SubmitHomeworkStatusDao, SubmitHomeworkStatusEntity> implements SubmitHomeworkStatusService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SubmitHomeworkStatusEntity> page = this.page(
                new Query<SubmitHomeworkStatusEntity>().getPage(params),
                new QueryWrapper<SubmitHomeworkStatusEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public SubmitHomeworkStatusEntity findUserSubmit(String userId, Long homeworkId) {
        return this.getOne(new QueryWrapper<SubmitHomeworkStatusEntity>().eq("user_id", userId).eq("homework_id", homeworkId));
    }

    @Override
    public SubmitHomeworkStatusEntity saveSubmitStatus(HomeworkEntity homeworkEntity, String id, int status) {
        long time = System.currentTimeMillis();
        SubmitHomeworkStatusEntity submitHomeworkStatusEntity = new SubmitHomeworkStatusEntity();
        submitHomeworkStatusEntity.setCreateTime(time);
        submitHomeworkStatusEntity.setHomeworkId(homeworkEntity.getId());
        submitHomeworkStatusEntity.setScore(0.0);
        submitHomeworkStatusEntity.setUpdateTime(time);
        submitHomeworkStatusEntity.setUserId(id);
        submitHomeworkStatusEntity.setStatus(status);
        this.save(submitHomeworkStatusEntity);
        return submitHomeworkStatusEntity;
    }



}
