/*
 * Copyright 2014-2017 Ainrif <support@ainrif.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

modulejs.define('sidebar', function () {

    function run() {
        var $sidebar = $('.sidebar');

        var $apiTitleEndpoints = $sidebar.find('.js_sidebar-endpoints .api__title');

        $apiTitleEndpoints.on('click', function (e) {
            var $title = $(this);
            var $apiMenu = $title.closest('.api');

            $apiMenu.toggleClass('api_active');

            if ($apiMenu.hasClass('api_active')) {
                location.hash = $title.data('link')
            }
        });

        var $apiToggleEndpoints = $sidebar.find('.js_sidebar-endpoints .api__toggle');

        $apiToggleEndpoints.on('click', function (e) {
            $(this)
                .closest('.api')
                .toggleClass('api_active');
        });

        var $apiTitleModel = $sidebar.find('.js_sidebar-model .api__title');

        $apiTitleModel.on('click', function (e) {
            location.hash = $(this).data('link')
        });

        var $groupTitle = $('.group__title');

        $groupTitle.on('click', function (e) {
            openGroupTitle($(this))
        })
    }

    /**
     * @param {jQuery} $groupTitle
     */
    function openGroupTitle($groupTitle) {
        $groupTitle.siblings('.group__content').removeClass('group__content_active');
        $groupTitle.next().addClass('group__content_active');
    }

    return {
        run: run,
        openGroupTitle: openGroupTitle
    }
});