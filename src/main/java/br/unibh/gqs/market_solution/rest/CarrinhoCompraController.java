package br.unibh.gqs.market_solution.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import br.unibh.gqs.market_solution.model.CarrinhoCompra;
import br.unibh.gqs.market_solution.model.ItemCompra;
import br.unibh.gqs.market_solution.service.CarrinhoCompraService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CarrinhoCompraController {

    @Autowired
    private CarrinhoCompraService carrinhoCompraService;

    @RequestMapping(value = "/carrinhocompra/{id}", method = RequestMethod.GET)
    public CarrinhoCompra getCarrinhoCompra(@PathVariable Long id){
        return carrinhoCompraService.getById(id);
    }

    @RequestMapping(value = "/carrinhocompra", method = RequestMethod.POST)
    public CarrinhoCompra addCarrinhoCompra(@RequestBody CarrinhoCompra carrinhocompra){
        return carrinhoCompraService.save(carrinhocompra);
    }

    @RequestMapping(value = "/carrinhocompra", method = RequestMethod.PUT)
    public CarrinhoCompra updateCarrinhoCompra(@RequestBody CarrinhoCompra carrinhocompra){
        return carrinhoCompraService.save(carrinhocompra);
    }

    @RequestMapping(value = "/carrinhocompra", method = RequestMethod.DELETE)
    public Map<String, String> deleteCarrinhoCompra(@RequestParam Long id){
        Map<String, String> status = new HashMap<>();
        if(carrinhoCompraService.exists(id)) {
            carrinhoCompraService.delete(id);
            status.put("Status", "CarrinhoCompra deleted successfully");
        } else {
            status.put("Status", "CarrinhoCompra not exist");
        }
        return status;
    }

    @RequestMapping(value = "/carrinhocompras", method = RequestMethod.GET)
    public List<CarrinhoCompra> getAllCarrinhoCompra(){
        return carrinhoCompraService.getAll();
    }

    @PutMapping("/carrinho/{id}/item")
    public CarrinhoCompra addItemToCarrinho(@PathVariable Long id, @RequestBody ItemCompra item) {
        return carrinhoCompraService.addItemCarrinhoCompra(id, item);
    }
}
