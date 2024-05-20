package com.juju.cozyformombackend3.global.auth.handler;

// @Component
// @Slf4j
// @RequiredArgsConstructor
// public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
//
//     @Override
//     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//         Authentication authentication) throws IOException, ServletException {
//
//         DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();
//
//         String id = defaultOAuth2User.getAttributes().get("id").toString();
//         String body = """
//             {"id":"%s"}
//             """.formatted(id);
//
//         response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//         response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//         PrintWriter writer = response.getWriter();
//         writer.println(body);
//         writer.flush();
//     }
// }
