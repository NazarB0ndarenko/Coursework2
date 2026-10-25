INSERT INTO users (email, first_name, last_name, password, role, auth_provider, created_at) VALUES
        ('john.doe@example.com', 'John', 'Doe', '$2a$10$7ORveveLJfaybJJFkFlm6.saQokfy6dnwk20epYs12mYnWzv/mZ8q', 'USER', 'LOCAL', NOW()),
        ('jane.smith@example.com', 'Jane', 'Smith', '$2a$10$RK/w1L8/8Ur.Mhd1rvKtCupTOlamfSTX.JO5tvJ5mO8pvC8levj5.', 'USER', 'LOCAL', NOW()),
        ('bob.johnson@example.com', 'Bob', 'Johnson', '$2a$10$jhUlzliCUze5jNF0hYoQMOt0nXPOc/b2oa/4RC9dIpOBMaD3BsyLq', 'USER', 'LOCAL', NOW()),
        ('alice.williams@example.com', 'Alice', 'Williams', '$2a$10$uYan4C1nNLRnT3vFAgIjfeKV4QsUycmUhvgLksvaz5EIDF/Ck/PRK', 'MODERATOR', 'LOCAL', NOW()),
        ('admin@example.com', 'Admin', 'User', '$2a$10$kDIN2EhhYjsdljUyeJcbS.1Hl3YYFBUtIizIyrI8Vt0NrTIlGoIKa', 'ADMIN', 'LOCAL', NOW());

INSERT INTO projects (title, description, created_at) VALUES
        ('E-Commerce Platform', 'Building a modern e-commerce platform with React and Spring Boot', NOW() - INTERVAL '30 days'),
        ('Mobile App Development', 'Cross-platform mobile app using React Native', NOW() - INTERVAL '20 days'),
        ('Internal Dashboard', 'Analytics dashboard for internal company metrics', NOW() - INTERVAL '10 days');

INSERT INTO project_configurations (project_id, is_public, max_participants, background_image) VALUES
        (1, TRUE, 10, 'https://images.unsplash.com/photo-1557821552-17105176677c'),
        (2, TRUE, 8, 'https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c'),
        (3, FALSE, 5, 'https://images.unsplash.com/photo-1551288049-bebda4e38f71');

INSERT INTO project_members (user_id, project_id, project_role, join_date) VALUES
        (1, 1, 'OWNER', NOW() - INTERVAL '30 days'),
        (2, 1, 'MANAGER', NOW() - INTERVAL '28 days'),
        (3, 1, 'USER', NOW() - INTERVAL '25 days'),
        (4, 1, 'USER', NOW() - INTERVAL '20 days');

INSERT INTO project_members (user_id, project_id, project_role, join_date) VALUES
        (2, 2, 'OWNER', NOW() - INTERVAL '20 days'),
        (3, 2, 'MANAGER', NOW() - INTERVAL '18 days'),
        (1, 2, 'USER', NOW() - INTERVAL '15 days');

INSERT INTO project_members (user_id, project_id, project_role, join_date) VALUES
        (5, 3, 'OWNER', NOW() - INTERVAL '10 days'),
        (4, 3, 'MANAGER', NOW() - INTERVAL '9 days'),
        (1, 3, 'USER', NOW() - INTERVAL '8 days');

INSERT INTO task_labels (title, color) VALUES
        ('Frontend', '#3B82F6'),
        ('Backend', '#10B981'),
        ('Bug', '#EF4444'),
        ('Feature', '#8B5CF6'),
        ('Documentation', '#F59E0B'),
        ('Urgent', '#DC2626'),
        ('Design', '#EC4899');

INSERT INTO tasks (title, description, priority, status, deadline, created_at, project_id) VALUES
        ('Setup React Project Structure', 'Initialize React app with TypeScript and configure routing', 'HIGH', 'DONE', NOW() - INTERVAL '20 days', NOW() - INTERVAL '28 days', 1),
        ('Design Database Schema', 'Create ERD and design database tables for products, users, orders', 'HIGH', 'DONE', NOW() - INTERVAL '18 days', NOW() - INTERVAL '27 days', 1),
        ('Implement User Authentication', 'Add JWT-based authentication with login and registration', 'CRITICAL', 'IN_PROGRESS', NOW() + INTERVAL '2 days', NOW() - INTERVAL '15 days', 1),
        ('Build Product Catalog API', 'REST API endpoints for products with search and filtering', 'HIGH', 'IN_PROGRESS', NOW() + INTERVAL '5 days', NOW() - INTERVAL '12 days', 1),
        ('Create Shopping Cart Feature', 'Frontend and backend for shopping cart functionality', 'MEDIUM', 'TODO', NOW() + INTERVAL '10 days', NOW() - INTERVAL '8 days', 1),
        ('Implement Payment Gateway', 'Integrate Stripe for payment processing', 'CRITICAL', 'TODO', NOW() + INTERVAL '15 days', NOW() - INTERVAL '5 days', 1),
        ('Add Product Reviews', 'Allow users to rate and review products', 'LOW', 'TODO', NOW() + INTERVAL '20 days', NOW() - INTERVAL '3 days', 1),
        ('Fix Checkout Bug', 'Cart items disappearing on page refresh', 'HIGH', 'FOR_REVIEW', NOW() + INTERVAL '1 day', NOW() - INTERVAL '2 days', 1);

INSERT INTO tasks (title, description, priority, status, deadline, created_at, project_id) VALUES
        ('Setup React Native Project', 'Initialize project with Expo and navigation', 'HIGH', 'DONE', NOW() - INTERVAL '15 days', NOW() - INTERVAL '18 days', 2),
        ('Design UI Mockups', 'Create Figma designs for all main screens', 'HIGH', 'DONE', NOW() - INTERVAL '12 days', NOW() - INTERVAL '17 days', 2),
        ('Implement Authentication Flow', 'Login, registration, and password reset screens', 'CRITICAL', 'IN_PROGRESS', NOW() + INTERVAL '3 days', NOW() - INTERVAL '10 days', 2),
        ('Build Home Screen', 'Main dashboard with featured content', 'MEDIUM', 'IN_PROGRESS', NOW() + INTERVAL '7 days', NOW() - INTERVAL '8 days', 2),
        ('Add Push Notifications', 'Setup Firebase Cloud Messaging', 'MEDIUM', 'TODO', NOW() + INTERVAL '12 days', NOW() - INTERVAL '5 days', 2),
        ('Implement Offline Mode', 'Cache data for offline access', 'LOW', 'TODO', NOW() + INTERVAL '20 days', NOW() - INTERVAL '3 days', 2);

INSERT INTO tasks (title, description, priority, status, deadline, created_at, project_id) VALUES
        ('Setup Analytics Backend', 'Create API endpoints for fetching metrics', 'HIGH', 'IN_PROGRESS', NOW() + INTERVAL '5 days', NOW() - INTERVAL '9 days', 3),
        ('Create Dashboard Layout', 'Build responsive dashboard with charts', 'HIGH', 'IN_PROGRESS', NOW() + INTERVAL '6 days', NOW() - INTERVAL '8 days', 3),
        ('Add Data Visualization', 'Integrate Chart.js for graphs and charts', 'MEDIUM', 'TODO', NOW() + INTERVAL '10 days', NOW() - INTERVAL '6 days', 3),
        ('Implement User Management', 'Admin panel for managing users and permissions', 'MEDIUM', 'TODO', NOW() + INTERVAL '15 days', NOW() - INTERVAL '4 days', 3);

INSERT INTO task_label_mapping (task_id, label_id) VALUES
       (1, 1),
       (2, 2),
       (3, 2), (3, 6),
       (4, 2),
       (5, 1), (5, 2), (5, 4),
       (6, 2), (6, 6),
       (7, 4),
       (8, 3), (8, 6);

INSERT INTO task_label_mapping (task_id, label_id) VALUES
       (9, 1),
       (10, 7),
       (11, 1), (11, 6),
       (12, 1), (12, 7),
       (13, 2), (13, 4),
       (14, 4);

INSERT INTO task_label_mapping (task_id, label_id) VALUES
        (15, 2),
        (16, 1), (16, 7),
        (17, 1),
        (18, 4);

INSERT INTO member_task_assignment (member_id, task_id, assigned_on) VALUES
        (1, 1, NOW() - INTERVAL '28 days'),
        (2, 2, NOW() - INTERVAL '27 days'),
        (1, 3, NOW() - INTERVAL '15 days'),
        (2, 4, NOW() - INTERVAL '12 days'),
        (3, 5, NOW() - INTERVAL '8 days'),
        (2, 6, NOW() - INTERVAL '5 days'),
        (4, 7, NOW() - INTERVAL '3 days'),
        (3, 8, NOW() - INTERVAL '2 days');

INSERT INTO member_task_assignment (member_id, task_id, assigned_on) VALUES
        (5, 9, NOW() - INTERVAL '18 days'),
        (6, 10, NOW() - INTERVAL '17 days'),
        (5, 11, NOW() - INTERVAL '10 days'),
        (7, 12, NOW() - INTERVAL '8 days'),
        (6, 13, NOW() - INTERVAL '5 days'),
        (7, 14, NOW() - INTERVAL '3 days');

INSERT INTO member_task_assignment (member_id, task_id, assigned_on) VALUES
        (9, 15, NOW() - INTERVAL '9 days'),
        (10, 16, NOW() - INTERVAL '8 days'),
        (9, 17, NOW() - INTERVAL '6 days'),
        (8, 18, NOW() - INTERVAL '4 days');

INSERT INTO project_discussions (title, created_at, posted_by, project_id) VALUES
        ('Architecture Discussion', NOW() - INTERVAL '25 days', 1, 1),
        ('UI/UX Feedback', NOW() - INTERVAL '15 days', 2, 1),
        ('Performance Optimization', NOW() - INTERVAL '10 days', 5, 2),
        ('Dashboard Design Review', NOW() - INTERVAL '7 days', 8, 3);


INSERT INTO project_discussion_messages (message, created_at, posted_by, discussion_id) VALUES
        ('Should we use microservices or monolithic architecture?', NOW() - INTERVAL '25 days', 1, 1),
        ('I think monolithic is better for MVP. We can split later if needed.', NOW() - INTERVAL '25 days', 2, 1),
        ('Agreed. Let''s focus on getting features out quickly first.', NOW() - INTERVAL '24 days', 1, 1),
        ('We should still plan for scalability though.', NOW() - INTERVAL '24 days', 4, 1);

INSERT INTO project_discussion_messages (message, created_at, posted_by, discussion_id) VALUES
        ('The checkout flow feels too long. Can we simplify?', NOW() - INTERVAL '15 days', 2, 2),
        ('Good point. Maybe combine shipping and payment into one step?', NOW() - INTERVAL '15 days', 3, 2),
        ('Let me create a prototype and we can test it.', NOW() - INTERVAL '14 days', 2, 2);

INSERT INTO project_discussion_messages (message, created_at, posted_by, discussion_id) VALUES
        ('App is loading slowly on Android devices. Any ideas?', NOW() - INTERVAL '10 days', 5, 3),
        ('We should implement lazy loading for images.', NOW() - INTERVAL '10 days', 6, 3),
        ('Also consider caching API responses.', NOW() - INTERVAL '9 days', 7, 3);

INSERT INTO project_discussion_messages (message, created_at, posted_by, discussion_id) VALUES
        ('What metrics should we prioritize on the main dashboard?', NOW() - INTERVAL '7 days', 8, 4),
        ('Revenue, active users, and conversion rate for sure.', NOW() - INTERVAL '7 days', 9, 4),
        ('Add a date range selector too.', NOW() - INTERVAL '6 days', 10, 4);


INSERT INTO task_discussion_message (author_id, message, task_id, posted_at) VALUES
        (1, 'Started implementing JWT tokens. Should be done by tomorrow.', 3, NOW() - INTERVAL '14 days'),
        (2, 'Great! Make sure to add refresh token logic too.', 3, NOW() - INTERVAL '14 days'),
        (1, 'Will do. Also adding password strength validation.', 3, NOW() - INTERVAL '13 days'),
        (3, 'Found the issue - localStorage was being cleared on navigation.', 8, NOW() - INTERVAL '1 day'),
        (2, 'Nice catch! Can you create a PR?', 8, NOW() - INTERVAL '1 day'),
        (3, 'PR is ready for review.', 8, NOW() - INTERVAL '12 hours'),
        (5, 'Designs are approved. Starting implementation now.', 11, NOW() - INTERVAL '9 days'),
        (6, 'Let me know if you need help with the API integration.', 11, NOW() - INTERVAL '9 days');


INSERT INTO notifications (user_id, title, message, created_at, has_been_read) VALUES
        (1, 'Task Assigned', 'You have been assigned to task: Implement User Authentication', NOW() - INTERVAL '15 days', TRUE),
        (1, 'New Comment', 'Jane commented on Authentication: "Make sure to add refresh token logic"', NOW() - INTERVAL '14 days', TRUE),
        (1, 'Deadline Approaching', 'Task "Implement User Authentication" is due in 2 days', NOW() - INTERVAL '1 day', FALSE),
        (1, 'New Discussion', 'New discussion started: UI/UX Feedback', NOW() - INTERVAL '15 days', TRUE),
        (2, 'Task Assigned', 'You have been assigned to task: Design Database Schema', NOW() - INTERVAL '27 days', TRUE),
        (2, 'Task Completed', 'John completed task: Setup React Project Structure', NOW() - INTERVAL '20 days', TRUE),
        (2, 'New Member', 'Alice joined E-Commerce Platform', NOW() - INTERVAL '20 days', TRUE),
        (2, 'Review Request', 'Bob requested review on: Fix Checkout Bug', NOW() - INTERVAL '12 hours', FALSE),
        (3, 'Task Assigned', 'You have been assigned to task: Create Shopping Cart Feature', NOW() - INTERVAL '8 days', TRUE),
        (3, 'Mention', 'Jane mentioned you in UI/UX Feedback discussion', NOW() - INTERVAL '15 days', TRUE),
        (3, 'Task Assigned', 'You have been assigned to task: Fix Checkout Bug', NOW() - INTERVAL '2 days', TRUE),
        (4, 'Project Invitation', 'You have been invited to join E-Commerce Platform', NOW() - INTERVAL '20 days', TRUE),
        (4, 'Task Assigned', 'You have been assigned to task: Add Product Reviews', NOW() - INTERVAL '3 days', FALSE),
        (4, 'New Message', 'New message in Architecture Discussion', NOW() - INTERVAL '24 days', TRUE);


INSERT INTO project_interactions (user_id, project_id, type, status, init_at, response_date) VALUES
        (2, 1, 'INVITATION', 'ACCEPTED', NOW() - INTERVAL '28 days', NOW() - INTERVAL '28 days'),
        (3, 1, 'INVITATION', 'ACCEPTED', NOW() - INTERVAL '25 days', NOW() - INTERVAL '25 days'),
        (4, 1, 'INVITATION', 'ACCEPTED', NOW() - INTERVAL '20 days', NOW() - INTERVAL '20 days'),
        (3, 2, 'INVITATION', 'ACCEPTED', NOW() - INTERVAL '18 days', NOW() - INTERVAL '18 days'),
        (1, 2, 'INVITATION', 'ACCEPTED', NOW() - INTERVAL '15 days', NOW() - INTERVAL '15 days'),
        (5, 1, 'INVITATION', 'PENDING', NOW() - INTERVAL '1 day', NULL),
        (3, 3, 'APPLICATION', 'DECLINED', NOW() - INTERVAL '5 days', NOW() - INTERVAL '4 days');


INSERT INTO evidences (url) VALUES
        ('https://example.com/screenshot1.png'),
        ('https://example.com/screenshot2.png'),
        ('https://example.com/log-file.txt');

INSERT INTO reports (reporter_user_id, accused_user_id, report_type, created_at, status) VALUES
        (4, 3, 'SPAM', NOW() - INTERVAL '5 days', 'IN_REVIEW'),
        (2, 1, 'OTHER', NOW() - INTERVAL '2 days', 'OPEN');


INSERT INTO reports_evidences (report_id, evidences_id) VALUES
        (1, 1),
        (1, 2),
        (2, 3);
