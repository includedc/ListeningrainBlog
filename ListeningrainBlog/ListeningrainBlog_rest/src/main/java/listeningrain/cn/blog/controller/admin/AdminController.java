package listeningrain.cn.blog.controller.admin;

import listeningrain.cn.blog.constant.ConstantsEnum;
import listeningrain.cn.blog.input.data.CommentsInputData;
import listeningrain.cn.blog.input.data.ContentsInputData;
import listeningrain.cn.blog.input.data.MetasInputData;
import listeningrain.cn.blog.input.data.UserShowInformationInputData;
import listeningrain.cn.blog.input.dto.PageInputDTO;
import listeningrain.cn.blog.input.dto.PojoInputDTO;
import listeningrain.cn.blog.output.data.*;
import listeningrain.cn.blog.output.dto.PageOutputDTO;
import listeningrain.cn.blog.output.dto.PojoOutputDTO;
import listeningrain.cn.blog.service.api.CommentsService;
import listeningrain.cn.blog.service.api.ContentsService;
import listeningrain.cn.blog.service.api.MetasService;
import listeningrain.cn.blog.service.api.UserShowInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * author: listeningrain
 * Date: 2018/10/3
 * Time: 21:05
 * Description: 后台管理控制类
 */
@Controller
@RequestMapping(path = "/admin")
public class AdminController {

    @Autowired
    private ContentsService contentsService;
    @Autowired
    private MetasService metasService;
    @Autowired
    private CommentsService commentsService;
    @Value("${website-url}")
    private String websiteUrl;

    @RequestMapping()
    public String index(){
        return "redirect:/admin/index";
    }

    //后台首页
    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String admin(ModelMap modelMap){

        AdminIndexOutputData adminIndexContentsCount = contentsService.getAdminIndexContentsCount();
        AdminIndexOutputData adminIndexComment = commentsService.getAdminIndexComment();
        AdminIndexOutputData adminIndexLink = metasService.getAdminIndexLink();
        modelMap.put("contentsCount",adminIndexContentsCount);
        modelMap.put("commentsCount",adminIndexComment);
        modelMap.put("linksCount",adminIndexLink);
        getComments(modelMap,null);
        motto(modelMap,null);
        modelMap.put("websiteUrl",websiteUrl);
        return "admin/index";
    }

    //去文章编辑页
    @RequestMapping(path = "/index/post", method = RequestMethod.GET)
    public String post(ModelMap modelMap){
        PageInputDTO<MetasInputData> pageInputDTO = new PageInputDTO();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setType(ConstantsEnum.CATEGORY);
        pageInputDTO.setPageSize(100);
        pageInputDTO.setData(metasInputData);
        PageOutputDTO<MetasOutputData> allClassify = metasService.getMetasByType(pageInputDTO);
        modelMap.addAttribute("allClassify",allClassify);
        return "admin/post";
    }

    //保存文章
    @RequestMapping(path = "/index/save", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO save(@RequestBody PojoInputDTO<ContentsInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = null;
        if(null != pojoInputDTO.getData().getCid()){
            pojoOutputDTO = contentsService.updateContent(pojoInputDTO);
        }else{
            pojoOutputDTO = contentsService.addContent(pojoInputDTO);
        }
        return pojoOutputDTO;
    }

    //文章列表页
    @RequestMapping(path = "/index/list", method = RequestMethod.GET)
    public String index(ModelMap modelMap,Integer pageNum){
        PageInputDTO pageInputDTO = new PageInputDTO<>();
        pageInputDTO.setPageSize(10);
        if(null == pageNum){
            pageNum = 1;
        }
        pageInputDTO.setPageNum(pageNum);

        PageOutputDTO<ContentsOutputData> contentsByPage = contentsService.getContentsByPage(pageInputDTO);
        modelMap.addAttribute("contents",contentsByPage);
        return "admin/list";
    }

    //删除文章
    @RequestMapping(path = "/index/deleteContent", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO deleteContent(@RequestBody PojoInputDTO<ContentsInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = contentsService.deleteContent(pojoInputDTO);
        return pojoOutputDTO;
    }

    //编辑文章
    @RequestMapping(path = "/index/editContent", method = RequestMethod.GET)
    public String toEditPage(ModelMap modelMap ,@RequestParam Integer cid){
        PojoInputDTO<ContentsInputData> pojoInputDTO = new PojoInputDTO<>();
        ContentsInputData contentsInputData = new ContentsInputData();
        if(null != contentsInputData){
            contentsInputData.setCid(cid);
        }
        pojoInputDTO.setData(contentsInputData);
        PojoOutputDTO<ContentsOutputData> contentsById = contentsService.getContentsById(pojoInputDTO);

        PageInputDTO<MetasInputData> pojoMetasInputDTO = new PageInputDTO();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setType("CLASSIFY");
        pojoMetasInputDTO.setData(metasInputData);
        PageOutputDTO<MetasOutputData> allClassify = metasService.getMetasByType(pojoMetasInputDTO);
        modelMap.addAttribute("allClassify",allClassify);
        modelMap.put("content",contentsById);
        return "/admin/post";

    }

    @RequestMapping(path = "/index/link",method = RequestMethod.GET)
    public String link(ModelMap modelMap, Integer pageNum){
        PageInputDTO<MetasInputData> pageInputDTO = new PageInputDTO();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setType("LINK");
        pageInputDTO.setData(metasInputData);
        if(null == pageNum){
            pageNum = 1;
        }
        pageInputDTO.setPageNum(pageNum);
        pageInputDTO.setPageSize(5);
        PageOutputDTO<MetasOutputData> allLinks = metasService.getMetasByType(pageInputDTO);
        modelMap.addAttribute("links",allLinks);
        return "admin/links";
    }

    /**
     * 接收前端的ajax请求(根据友链的id获取内容)
     */
    @RequestMapping(path = "/index/editLink", method = RequestMethod.GET)
    @ResponseBody
    public PojoOutputDTO<MetasOutputData> editLink(@RequestParam Integer mid){
        PojoInputDTO<MetasInputData> pojoInputDTO = new PojoInputDTO<>();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setMid(mid);
        pojoInputDTO.setData(metasInputData);
        PojoOutputDTO<MetasOutputData> metasById = metasService.getMetasById(pojoInputDTO);
        return  metasById;
    }

    @RequestMapping(path = "/index/deleteLink", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO deleteLink(@RequestBody PojoInputDTO<MetasInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = metasService.deleteMetasById(pojoInputDTO);
        return pojoOutputDTO;
    }

    @RequestMapping(path = "/index/updateLink", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO updateLinkOrAdd(@RequestBody PojoInputDTO<MetasInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = null;
        if(!StringUtils.isEmpty(pojoInputDTO.getData().getMid())){
            //存在id，执行更新操作
           pojoOutputDTO = metasService.updateMetas(pojoInputDTO);
        }else{
            //不存在，则执行新增操作
         pojoOutputDTO = metasService.addMetas(pojoInputDTO);
        }
        return pojoOutputDTO;
    }

    //获取分类
    @RequestMapping(path = "/index/classify",method = RequestMethod.GET)
    public String classify(ModelMap modelMap, Integer pageNum){
        PageInputDTO<MetasInputData> pageInputDTO = new PageInputDTO();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setType(ConstantsEnum.CATEGORY);
        pageInputDTO.setData(metasInputData);
        if(null == pageNum){
            pageNum = 1;
        }
        pageInputDTO.setPageNum(pageNum);
        pageInputDTO.setPageSize(5);
        PageOutputDTO<MetasOutputData> allClassify = metasService.getMetasByType(pageInputDTO);
        modelMap.addAttribute("allClassify",allClassify);
        return "admin/category";
    }

    //获取名言
    @RequestMapping(path = "/index/motto",method = RequestMethod.GET)
    public String motto(ModelMap modelMap, Integer pageNum){
        PageInputDTO<MetasInputData> pageInputDTO = new PageInputDTO();
        MetasInputData metasInputData = new MetasInputData();
        metasInputData.setType("MOTTO");
        pageInputDTO.setData(metasInputData);
        if(null == pageNum){
            pageNum = 1;
        }
        pageInputDTO.setPageNum(pageNum);
        pageInputDTO.setPageSize(5);
        PageOutputDTO<MetasOutputData> allClassify = metasService.getMetasByType(pageInputDTO);
        modelMap.addAttribute("mottos",allClassify);
        return "admin/motto";
    }

    @RequestMapping(path = "/index/updateCategory", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO updateCategoryOrAdd(@RequestBody PojoInputDTO<MetasInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = null;
        if(!StringUtils.isEmpty(pojoInputDTO.getData().getMid())){
            //存在id，执行更新操作
            pojoOutputDTO = metasService.updateMetas(pojoInputDTO);
        }else{
            //不存在，则执行新增操作
            pojoOutputDTO = metasService.addMetas(pojoInputDTO);
        }
        return pojoOutputDTO;
    }

    @RequestMapping(path = "/index/comments",method = RequestMethod.GET)
    public String getComments(ModelMap modelMap,Integer pageNum){
        PageInputDTO pageInputDTO = new PageInputDTO<>();
        pageInputDTO.setPageSize(10);
        if(null == pageNum){
            pageNum = 1;
        }
        pageInputDTO.setPageNum(pageNum);

        PageOutputDTO<CommentsOutputData> commentsByPage = commentsService.getCommentsByPage(pageInputDTO);
        modelMap.addAttribute("comments",commentsByPage);
        return "admin/comments";
    }

    @RequestMapping(path = "/index/deleteComment", method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO deleteComment(@RequestBody PojoInputDTO<CommentsInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = commentsService.deleteComment(pojoInputDTO);
        return pojoOutputDTO;
    }

    @Autowired
    private UserShowInformationService userShowInformationService;
    @RequestMapping(path = "/index/website")
    public String website(ModelMap modelMap){
        PojoOutputDTO<UserShowInformationOutputData> userShowInformation = userShowInformationService.getUserShowInformation();
        modelMap.put("website",userShowInformation);
        return "admin/personInformation";
    }

    @RequestMapping(path = "/index/updateWebsite",method = RequestMethod.POST)
    @ResponseBody
    public PojoOutputDTO updateWebsite(@RequestBody PojoInputDTO<UserShowInformationInputData> pojoInputDTO){
        PojoOutputDTO pojoOutputDTO = userShowInformationService.updateUsershowInformation(pojoInputDTO);
        return pojoOutputDTO;
    }
}
