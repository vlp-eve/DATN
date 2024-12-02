package com.poly.datn.Controller.User;


import com.poly.datn.Entity.Cart.Cart;
import com.poly.datn.Entity.Cart.CartDetail;
import com.poly.datn.Entity.Payment.Method;
import com.poly.datn.Entity.User.User;
import com.poly.datn.Service.CartDetailService;
import com.poly.datn.Service.CartService;
import com.poly.datn.Service.MethodService;
import com.poly.datn.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private CartDetailService cartDetailService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;


    @Autowired
    private MethodService methodService;

    @GetMapping
    public String getCartDetail(Model model, HttpServletRequest request) {
        model.addAttribute("css", "/assetss/css/cart.css");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                // Tìm `User` dựa trên username
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    Cart cart = cartService.getCartByUser(user);
                    List<CartDetail> cartDetailList = cartDetailService.getAllCartDetailsForCart(cart.getId());

                    if (cartDetailList != null && !cartDetailList.isEmpty()) {
                        model.addAttribute("cartDetailList", cartDetailList);
                        model.addAttribute("cart", cart);

                    } else {
                        model.addAttribute("message", "Giỏ hàng của bạn hiện không có sản phẩm.");
                    }

                    return "assetss/form/cart";
                }
            }
        }

        return "redirect:/security/login/form";
    }


    @PostMapping("/add")
    public String addProductToCart(@RequestParam Long storeId,
                                   @RequestParam Double quantity,
                                   RedirectAttributes redirectAttributes, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("Authentication is null");
            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/login";
        } else {
            System.out.println("Authentication is available");
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("Authentication available: " + authentication.getName());
        }
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);
                if (user != null) {
                    try {
                        CartDetail cartDetail = cartDetailService.addProductToCart(user, storeId, quantity);
                        Cart cart = cartService.getCartByUser(user);
                        List<CartDetail> cartDetailList = cartDetailService.getAllCartDetailsForCart(cart.getId());
                        redirectAttributes.addFlashAttribute("cartDetailList", cartDetailList);
                        redirectAttributes.addFlashAttribute("cart", cart);
                        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng");
                        return "redirect:/store";
                    } catch (Exception e) {
                        e.printStackTrace();
                        redirectAttributes.addFlashAttribute("message", "Có lỗi xảy ra: " + e.getMessage());
                        return "redirect:/store";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại.");
                    return "redirect:/store";
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy thông tin người dùng.");
                return "redirect:/store";
            }
        } else {

            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/security/login/form";
        }
    }


    @PostMapping("/detail/add")
    public String addProductDetailToCart(@RequestParam Long storeId,
                                         @RequestParam Double quantity,
                                         RedirectAttributes redirectAttributes, Model model) {
        if (quantity <= 0) {
            model.addAttribute("errorMessage", "Số lượng không thể là số âm hoặc bằng 0.");
            return "redirect:/store/" + storeId;  // Chuyển hướng về trang chi tiết sản phẩm
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu authentication là null
        if (authentication == null) {
            System.out.println("Authentication is null");
            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/security/login/form";
        } else {
            System.out.println("Authentication is available");
            System.out.println("Principal: " + authentication.getPrincipal());
            System.out.println("Authentication available: " + authentication.getName());
        }

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    try {
                        CartDetail cartDetail = cartDetailService.addProductToCart(user, storeId, quantity);

                        Cart cart = cartService.getCartByUser(user);
                        List<CartDetail> cartDetailList = cartDetailService.getAllCartDetailsForCart(cart.getId());

                        redirectAttributes.addFlashAttribute("addCartDetailList", cartDetailList);
                        redirectAttributes.addFlashAttribute("cart", cart);
                        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng");

                        return "redirect:/store/" + storeId;
                    } catch (Exception e) {
                        e.printStackTrace();
                        redirectAttributes.addFlashAttribute("message", e.getMessage());
                        return "redirect:/store/" + storeId;
                    }
                } else {
                    redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại.");
                    return "redirect:/store/" + storeId;
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "Không tìm thấy thông tin người dùng.");
                return "redirect:/store/" + storeId;
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return "redirect:/security/login/form";
        }
    }

    @PostMapping("/delete")
    public String deleteCartDetail(@RequestParam Long cartDetailId, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu authentication là null
        if (authentication == null) {
            System.out.println("Authentication is null");
            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để xóa sản phẩm khỏi giỏ hàng.");
            return "redirect:/security/login/form";
        }

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    // Lấy Cart của người dùng
                    Cart cart = cartService.getCartByUser(user);
                    try {
                        // Gọi service để xóa CartDetail
                        cartDetailService.deleteCartDetail(cart.getId(), cartDetailId);
                        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng.");
                    } catch (EntityNotFoundException e) {
                        // Nếu không tìm thấy CartDetail, ném ngoại lệ sẽ được bắt ở đây
                        redirectAttributes.addFlashAttribute("message", "Không tìm thấy sản phẩm trong giỏ hàng.");
                    }

                    // Quay lại trang giỏ hàng
                    return "redirect:/cart";
                } else {
                    redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại.");
                    return "redirect:/security/login/form";
                }
            }
        }

        // Nếu người dùng không đăng nhập, chuyển hướng đến trang đăng nhập
        redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để xóa sản phẩm khỏi giỏ hàng.");
        return "redirect:/security/login/form";
    }

    @PostMapping("/deleteAll")
    public String removeAllProductsFromCart(RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu authentication là null
        if (authentication == null) {
            System.out.println("Authentication is null");
            redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để xóa sản phẩm khỏi giỏ hàng.");
            return "redirect:/security/login/form";
        }

        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    // Lấy Cart của người dùng
                    Cart cart = cartService.getCartByUser(user);
                    try {
                        cartService.removeAllProductFromCart(cart.getId());
                        redirectAttributes.addFlashAttribute("message", "Sản phẩm đã được xóa khỏi giỏ hàng.");
                    } catch (EntityNotFoundException e) {
                        // Nếu không tìm thấy CartDetail, ném ngoại lệ sẽ được bắt ở đây
                        redirectAttributes.addFlashAttribute("message", "Không tìm thấy sản phẩm trong giỏ hàng.");
                    }

                    // Quay lại trang giỏ hàng
                    return "redirect:/cart";
                } else {
                    redirectAttributes.addFlashAttribute("message", "Người dùng không tồn tại.");
                    return "redirect:/security/login/form";
                }
            }
        }

        // Nếu người dùng không đăng nhập, chuyển hướng đến trang đăng nhập
        redirectAttributes.addFlashAttribute("message", "Bạn cần phải đăng nhập để xóa sản phẩm khỏi giỏ hàng.");
        return "redirect:/security/login/form";
    }


    @GetMapping("/checkout")
    public String checkoutForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                User user = userService.findByUsernameUser(username);

                if (user != null) {
                    model.addAttribute("user", user);

                    // Lấy danh sách phương thức thanh toán
                    List<Method> methods = methodService.getAllMethod();
                    model.addAttribute("methods", methods);
                    Cart cart = cartService.getCartByUser(user);
                    // Lấy danh sách sản phẩm trong giỏ hàng
                    List<CartDetail> cartDetailList = cartDetailService.getAllCartDetailsForCart(cart.getId());
                    model.addAttribute("cartDetailList", cartDetailList);
                    model.addAttribute("css","/assetss/css/checkout.css");
                    model.addAttribute("cart", cart);
                    return "assetss/form/checkout";
                }
            }
        }
        return "redirect:/security/login/form";
    }



}
