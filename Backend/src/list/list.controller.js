const listService = require('./list.service.js');

async function createList(req, res){
    try{
        listId = await listService.createList(req.body);
        res.status(201).json({ message: 'List created successfully', listId });

    }catch(error){
        console.log(error);
        res.status(500).json({error});
    }
}

async function getAllLists(req, res){
    try{
        const result = await listService.getAllLists();
        res.status(200).json({
            success: true,
            message: "All Lists sent",
            payload: result
        });
    }catch(error){
        res.status(500).json(error);
    }
}

async function getListByName(req, res){
    try{
        const { listName } = req.query;
        const result = await listService.getListByName(listName);
        res.status(200).json(result);
    }catch(error){
        res.status(500).json(error);
    }
}

module.exports = {
    createList,
    getAllLists,
    getListByName
};