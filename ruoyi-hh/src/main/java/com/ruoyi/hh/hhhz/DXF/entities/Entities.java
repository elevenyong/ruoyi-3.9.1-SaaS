package com.ruoyi.hh.hhhz.DXF.entities;

import com.ruoyi.hh.hhhz.DXF.entities.base.*;

import java.util.*;

/**
 * 0
 * SECTION
 * 2
 * ENTITIES
 * ......
 * 0
 * ENDSEC
 */
public class Entities {

    /**
     * 保存所有entity
     */
    private final List<BaseEntity> entities;
    /**
     * 维护Entities的数量状态
     */
    private final HashMap<String, Integer> statusMap = new HashMap<>();
    public int thisHandle = 769;
    /**
     * 保存所有handle
     */
    public HashSet<String> handles = new HashSet<>();
    /**
     * 保存所有区域填充
     */
    public Map<Integer, List<BaseEntity>> hatchs = new HashMap<>();

    public Entities() {
        this.entities = new ArrayList<>();
    }

    public Entities(List<BaseEntity> entities) {
        this.entities = entities;
    }


    /**
     * 向Entities中添加一个实体，在这里完成handle赋值
     *
     * @param entity Entity
     * @return Entities List
     */
    public List<BaseEntity> addEntity(BaseEntity entity) {
        entity.setHandle(getNewHandle());
        entities.add(entity);
        System.out.println("Add:" + entity.getClass().getSimpleName() + "--" + entity.getHandle());
        updateStatus(entity, 1);
        return entities;
    }

    public Map<Integer, List<BaseEntity>> getHatchs() {
        return hatchs;
    }

    public void setHatchs(Map<Integer, List<BaseEntity>> hatchs) {
        this.hatchs = hatchs;
    }

    /**
     * 移除一个entity，并维护handles和statusMap
     *
     * @param entity
     * @return
     */
    public List<BaseEntity> deleteEntity(BaseEntity entity) {
        entities.remove(entity);
        handles.remove(entity.getHandle());
        updateStatus(entity, -1);
        return entities;
    }

    public int getEntitiesCount() {
        return entities.size();
    }

    /**
     * 显示当前Entities种类计数
     */
    public void showEntitiesCountStatus() {
        int total = 0;
        Set<String> keySet = statusMap.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            int value = statusMap.get(key);
            if (value <= 0) {
                iterator.remove();
                continue;
            }
            System.out.println(key + ": " + value);
            total += value;
        }
        System.out.println("Total: " + total);
        System.out.println("Handles:" + Arrays.toString(handles.toArray()));
    }

    public Entities getEntities() {
        return this;
    }

    public String getEntitiesAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("0\nSECTION\n2\nENTITIES\n");
        for (BaseEntity entity : entities) {
            sb.append(entity.toDXF());
        }
        sb.append("0\nENDSEC\n");
        return sb.toString();
    }

    /**
     * 更新Entities状态
     *
     * @param entity Entity
     * @param status 状态，增加：1，减少：-1
     */
    private void updateStatus(BaseEntity entity, int status) {
        if (entity instanceof Arc) {
            statusMap.put("Arc", statusMap.getOrDefault("Arc", 0) + status);
        } else if (entity instanceof Circle) {
            statusMap.put("Circle", statusMap.getOrDefault("Circle", 0) + status);
        } else if (entity instanceof PLine) {
            statusMap.put("PLine", statusMap.getOrDefault("PLine", 0) + status);
        } else if (entity instanceof Line) {
            statusMap.put("Line", statusMap.getOrDefault("Line", 0) + status);
        } else if (entity instanceof Point) {
            statusMap.put("Point", statusMap.getOrDefault("Point", 0) + status);
        } else if (entity instanceof Text) {
            statusMap.put("Text", statusMap.getOrDefault("Text", 0) + status);
        } else if (entity instanceof Ellipse) {
            statusMap.put("Ellipse", statusMap.getOrDefault("Ellipse", 0) + status);
        } else if (entity instanceof Ray) {
            statusMap.put("Ray", statusMap.getOrDefault("Ray", 0) + status);
        }
    }

    /**
     * 获取一个Handle
     *
     * @return handle
     */
    public String getNewHandle() {
        String handle = "300";
        if (handles.isEmpty()) {
            handles.add(handle);
        } else {
            if (thisHandle >= 768 && thisHandle < 9000) {
                handle = Integer.toHexString(thisHandle).toUpperCase();
                thisHandle++;
                handles.add(handle);
            } else {
                throw new RuntimeException("handle out of limit!");
            }
            /*do {
                int handleNumber = (int) Math.ceil(Math.random() * 1000);
                if (handleNumber > 768 && handleNumber < 4094) {
                    handle = Integer.toHexString(handleNumber).toUpperCase();
                }
            } while (handles.contains(handle));
            handles.add(handle);*/
        }
        return handle;
    }
}
